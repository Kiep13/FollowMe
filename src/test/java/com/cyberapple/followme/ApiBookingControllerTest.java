package com.cyberapple.followme;

import com.cyberapple.followme.entities.Country;
import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.entities.Participation;
import com.cyberapple.followme.records.BookingInput;
import com.cyberapple.followme.records.ParticipantInput;
import com.cyberapple.followme.repositories.ExcursionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test") 
public class ApiBookingControllerTest {
        @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExcursionRepository excursionRepository;

    @BeforeEach
    void setUp() {
        excursionRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void registerForExcursionAndGetListOfMyExcursions() throws Exception {
        prepareExcursionList();
        Excursion excursionBratislava = getBratislavaExcursion();

        ParticipantInput participant = new ParticipantInput("Alex", "Duggan", 
                LocalDate.of(1981, 1, 1), "gb", "AB1234567");

        BookingInput participationInput = new BookingInput(List.of(participant));

        mockMvc.perform(post("/api/bookings/" + excursionBratislava.getId() + "/add")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(participationInput)))
               .andExpect(status().isCreated())
               .andReturn();

        MvcResult result = mockMvc.perform(get("/api/bookings/my"))
                .andExpect(status().isOk())
                .andReturn();

          List<Participation> participations = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<>() {}
        );

        assertThat(participations).hasSize(1);

        Participation participation = participations.get(0);

        assertThat(participation.getExcursion().getId()).isEqualTo(excursionBratislava.getId());
        assertThat(participation.getParticipants().get(0).getFirstName()).isEqualTo(participant.firstName());
        assertThat(participation.getParticipants().get(0).getLastName()).isEqualTo(participant.lastName());
        assertThat(participation.getParticipants().get(0).getDateOfBirth()).isEqualTo(participant.dateOfBirth());
        assertThat(participation.getParticipants().get(0).getCitizenship()).isEqualTo(participant.citizenship());
        assertThat(participation.getParticipants().get(0).getPassportNumber()).isEqualTo(participant.passportNumber());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getListOfEmptyExcursions() throws Exception {
        prepareExcursionList();

        MvcResult result = mockMvc.perform(get("/api/bookings/my"))
                .andExpect(status().isOk())
                .andReturn();

        List<Participation> participations = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<>() {}
        );

        assertThat(participations).hasSize(0);
    }

    @Test
    void registerForExcursionUnauthorized() throws Exception {
        prepareExcursionList();
        Excursion excursionVienna = getViennaExcursion();

        ParticipantInput participant = new ParticipantInput("Alex", "Duggan", 
                LocalDate.of(1981, 1, 1), "gb", "AB1234567");

        BookingInput participationInput = new BookingInput(List.of(participant));

        mockMvc.perform(post("/api/bookings/" + excursionVienna.getId() + "/add")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(participationInput)))
               .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void registerForExcursionInvalidExcursionId() throws Exception {
        prepareExcursionList();

        ParticipantInput participant = new ParticipantInput("Alex", "Duggan", 
                LocalDate.of(1981, 1, 1), "gb", "AB1234567");

        BookingInput participationInput = new BookingInput(List.of(participant));

        mockMvc.perform(post("/api/bookings/non-existing-id/add")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(participationInput)))
               .andExpect(status().isForbidden())
               .andExpect(jsonPath("$.message").value("Invalid format for excursion id: non-existing-id"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void registerForExcursionNotFoundExcursionId() throws Exception {
        prepareExcursionList();

        ParticipantInput participant = new ParticipantInput("Alex", "Duggan", 
                LocalDate.of(1981, 1, 1), "gb", "AB1234567");

        BookingInput participationInput = new BookingInput(List.of(participant));

        mockMvc.perform(post("/api/bookings/9784b77c-72d2-45a0-809a-24bc4d23b0ae/add")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(participationInput)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Excursion not found with id: 9784b77c-72d2-45a0-809a-24bc4d23b0ae"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void registerForExcursionEmptyParticipantName() throws Exception {
        prepareExcursionList();
        Excursion excursion = getBratislavaExcursion();

        ParticipantInput participant = new ParticipantInput("", "Duggan", 
                LocalDate.of(1981, 1, 1), "gb", "AB1234567");

        BookingInput participationInput = new BookingInput(List.of(participant));

        mockMvc.perform(post("/api/bookings/" + excursion.getId() + "/add")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(participationInput)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Participant first name is required"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void registerForExcursionInvalidCountryCode() throws Exception {
        prepareExcursionList();
        Excursion excursion = getBratislavaExcursion();

        ParticipantInput participant = new ParticipantInput("Alex", "Duggan", 
                LocalDate.of(1981, 1, 1), "db", "AB1234567");

        BookingInput participationInput = new BookingInput(List.of(participant));

        mockMvc.perform(post("/api/bookings/" + excursion.getId() + "/add")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(participationInput)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Invalid country code"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void registerForExcursionInvalidPassportNumber() throws Exception {
        prepareExcursionList();
        Excursion excursion = getBratislavaExcursion();

        ParticipantInput participant = new ParticipantInput("Alex", "Duggan", 
                LocalDate.of(1981, 1, 1), "gb", "1234567890987654321");

        BookingInput participationInput = new BookingInput(List.of(participant));

        mockMvc.perform(post("/api/bookings/" + excursion.getId() + "/add")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(participationInput)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Invalid passport number format. Example: AB1234567"));
    }

    private void prepareExcursionList() {
        Excursion excursionBratislava = new Excursion();
        excursionBratislava.setTitle("Bratislava City Tour");
        excursionBratislava.setImageUrl("http://example.com/image.jpg");
        excursionBratislava.setDescription("Excursion description");
        excursionBratislava.setDate(LocalDate.of(2025, 10, 1));
        excursionBratislava.setPrice(7);
        excursionBratislava.setAmountOfPlaces(15);
        excursionBratislava.setCountry(Country.sk);
        excursionRepository.save(excursionBratislava);

        Excursion excursionVienna = new Excursion();
        excursionVienna.setTitle("Vienna City Tour");
        excursionVienna.setImageUrl("http://example.com/image.jpg");
        excursionVienna.setDescription("Excursion description");
        excursionVienna.setDate(LocalDate.of(2025, 10, 7));
        excursionVienna.setPrice(9);
        excursionVienna.setAmountOfPlaces(15);
        excursionVienna.setCountry(Country.at);
        excursionRepository.save(excursionVienna);

        Excursion excursionBudapest = new Excursion();
        excursionBudapest.setTitle("Budapest City Tour");
        excursionBudapest.setImageUrl("http://example.com/image.jpg");
        excursionBudapest.setDescription("Excursion description");
        excursionBudapest.setDate(LocalDate.of(2025, 10, 14));
        excursionBudapest.setPrice(10);
        excursionBudapest.setAmountOfPlaces(15);
        excursionBudapest.setCountry(Country.hu);
        excursionRepository.save(excursionBudapest);
    }

    private Excursion getBratislavaExcursion() {
        return excursionRepository.findByTitle("Bratislava City Tour");
    }

    private Excursion getViennaExcursion() {
        return excursionRepository.findByTitle("Vienna City Tour");
    }

    private Excursion getBudapestExcursion() {
        return excursionRepository.findByTitle("Budapest City Tour");
    } 
}
