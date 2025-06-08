package com.cyberapple.followme.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyberapple.followme.entities.Participation;
import com.cyberapple.followme.entities.User;
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
    public void registerForExcursion(String id, Participation participation) {
        participation.setExcursion(this.excursionRepository.findById(id).orElse(null));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }
        User user = this.userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        
        participation.setUser(user);

        // TODO: is it really good practise?
        participation.getParticipants().forEach(participant -> {
            participant.setParticipation(participation);
        });

        participationRepository.save(participation);
    }

    public Iterable<Participation> getBookedExcursions() {
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
