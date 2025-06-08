package com.cyberapple.followme.services;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyberapple.followme.entities.Participant;
import com.cyberapple.followme.entities.Participation;
import com.cyberapple.followme.entities.User;
import com.cyberapple.followme.records.BookingInput;
import com.cyberapple.followme.repositories.ExcursionRepository;
import com.cyberapple.followme.repositories.ParticipationRepository;
import com.cyberapple.followme.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingService {
    private final ExcursionRepository excursionRepository;
    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void registerForExcursion(String excursionId, BookingInput bookingInput) {

        Participation participation = new Participation();
        participation.setExcursion(this.excursionRepository.findById(excursionId).orElse(null));

        // TODO: Should it be on AOP?
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }
        User user = this.userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        
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

        // TODO: Should it be on AOP?
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }
        User user = this.userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Iterable<Participation> participations = this.participationRepository.findByUser(user);

        return participations;
    }
}
