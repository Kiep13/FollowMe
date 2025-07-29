package com.cyberapple.followme.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.cyberapple.followme.entities.Participant;
import com.cyberapple.followme.entities.Participation;
import com.cyberapple.followme.entities.User;
import com.cyberapple.followme.exceptions.NotFoundException;
import com.cyberapple.followme.records.BookingInput;
import com.cyberapple.followme.records.ParticipantInput;
import com.cyberapple.followme.repositories.ExcursionRepository;
import com.cyberapple.followme.repositories.ParticipationRepository;
import com.cyberapple.followme.validators.BookingValidator;
import com.cyberapple.followme.validators.ExcursionValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingService {
    private final ExcursionRepository excursionRepository;
    private final ParticipationRepository participationRepository;
    private final AuthenticationService authenticationService;

    private final InvoiceService invoiceService;

    private final ExcursionValidator excursionIdValidator;
    private final BookingValidator bookingValidator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void registerForExcursion(String excursionId, BookingInput bookingInput) throws NotFoundException {
        var uuidExcursionId = excursionIdValidator.validateExcursionId(excursionId);

        int amountOfParticipants = getAmountOfParticipants(bookingInput.participants());
        bookingValidator.validateFreeSears(uuidExcursionId, amountOfParticipants);

        Participation participation = new Participation();
        participation.setExcursion(this.excursionRepository.findById(uuidExcursionId).orElse(null));

        User user = authenticationService.getAuthenticatedUser();
        
        participation.setUser(user);
        participation.setParticipants(new ArrayList<Participant>());

        bookingInput.participants().forEach(participantInput -> {
            Participant participant = new Participant(participantInput);
            participant.setParticipation(participation);
            participation.getParticipants().add(participant);
        });

        participation.setCreatedAt(LocalDate.now());

        participationRepository.save(participation);

        var excursion = participation.getExcursion();
        var invoiceAmount = excursion.getPrice() * amountOfParticipants;
        var description = String.format("Booking for excursion '%s' with %d participants", excursion.getTitle(), amountOfParticipants);
        invoiceService.performInvoiceRequest(invoiceAmount, description);
    }

    public Iterable<Participation> getBookedExcursions() {
        User user = authenticationService.getAuthenticatedUser();

        return this.participationRepository.findByUser(user);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void cancelBooking(String bookingId) throws NotFoundException {
        var uuidBookingId = UUID.fromString(bookingId);
        Participation participation = participationRepository.findById(uuidBookingId)
                .orElseThrow(() -> new NotFoundException("Participation not found"));

        User user = authenticationService.getAuthenticatedUser();
        if (!participation.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("Participation not found for the authenticated user");
        }

        participationRepository.delete(participation);
    }

    private int getAmountOfParticipants(Iterable<ParticipantInput> participants) {
        int amountOfParticipants = 0;
        for (var ignored : participants) {
            amountOfParticipants++;
        }

        return amountOfParticipants;
    }
}
