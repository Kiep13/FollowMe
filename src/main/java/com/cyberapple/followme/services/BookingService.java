package com.cyberapple.followme.services;

import java.time.LocalDate;
import java.util.ArrayList;

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

    private final ExcursionValidator excursionIdValidator;
    private final BookingValidator bookingValidator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void registerForExcursion(String excursionId, BookingInput bookingInput) throws NotFoundException {
        excursionIdValidator.validateExcursionId(excursionId);

        int amountOfParticipants = getAmountOfParticipants(bookingInput.participants());
        bookingValidator.validateFreeSears(excursionId, amountOfParticipants);

        Participation participation = new Participation();
        participation.setExcursion(this.excursionRepository.findById(excursionId).orElse(null));

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
    }

    public Iterable<Participation> getBookedExcursions() {
        User user = authenticationService.getAuthenticatedUser();

        Iterable<Participation> participations = this.participationRepository.findByUser(user);

        return participations;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void cancelBooking(String bookingId) throws NotFoundException {
        Participation participation = participationRepository.findById(bookingId)
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
