package com.cyberapple.followme;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.entities.Country;
import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.records.ExcursionInput;
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
public class ApiExcursionController {

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
    void getPriceRangeSucess() throws Exception {
        prepareExcursionList();;
        
        mockMvc.perform(get("/api/excursions/price-range"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.minPrice").value(7))
            .andExpect(jsonPath("$.maxPrice").value(10));
    }

    @Test
    void getAllExcursionsSuccess() throws Exception {
        prepareExcursionList();

        MvcResult result = mockMvc.perform(get("/api/excursions"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").isArray())
           .andReturn();
        
        List<ExcursionDto> excursions = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<>() {}
        );

        assertThat(excursions).hasSize(3);
        assertThat(excursions.get(0).getTitle()).isEqualTo("Bratislava City Tour");
        assertThat(excursions.get(1).getTitle()).isEqualTo("Vienna City Tour");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createExcursionSuccess() throws Exception {
        ExcursionInput input = new ExcursionInput(
            "Bratislava City Tour",
            "http://example.com/image.jpg",
            "Excursion description",
            LocalDate.of(2025, 10, 1),
            7,
            15,
            Country.sk
        );

        mockMvc.perform(post("/api/excursions/add")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(input)))
               .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/api/excursions"))
                .andExpect(status().isOk())
                .andReturn();

        List<ExcursionDto> excursions = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<>() {}
        );

        assertThat(excursions).hasSize(1);
        assertThat(excursions.get(0).getTitle()).isEqualTo(input.title());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createExcursionForbidden() throws Exception {
        ExcursionInput input = new ExcursionInput(
            "Bratislava City Tour",
            "http://example.com/image.jpg",
            "Excursion description",
            LocalDate.of(2025, 10, 1),
            7,
            15,
            Country.sk
        );

        mockMvc.perform(post("/api/excursions/add")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(input)))
               .andExpect(status().isForbidden());
    }

    @Test
    void getExcursionByIdSuccess() throws Exception {
        prepareExcursionList();
        Iterable<Excursion> list = excursionRepository.findAll();
        Excursion excursionBratislava = list.iterator().next();
        
        MvcResult result = mockMvc.perform(get("/api/excursions/{id}", excursionBratislava.getId()))
            .andExpect(status().isOk())
            .andReturn();

        ExcursionDto resultExcursion = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            ExcursionDto.class
        );

        assertThat(resultExcursion.getTitle()).isEqualTo(excursionBratislava.getTitle());
    }

    @Test
    void getExcursionByIdNonValidId() throws Exception {
        mockMvc.perform(get("/api/excursions/{id}", "non-valid-id"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Invalid format for excursion id: non-existing-id"));
    }

    @Test
    void getExcursionByIdNonFoundId() throws Exception {
        mockMvc.perform(get("/api/excursions/{id}", "9784b77c-72d2-45a0-809a-24bc4d23b0ae"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Excursion not found with id: 9784b77c-72d2-45a0-809a-24bc4d23b0ae"));
    }

    @Test
    void getExcursionByIdEmptyId() throws Exception {
        mockMvc.perform(get("/api/excursions/{id}", ""))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Excursion id cannot be null or empty"));
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
}