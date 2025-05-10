package com.cyberapple.followme.services;

import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.entities.Participant;
import com.cyberapple.followme.repositories.ExcursionRepository;
import com.cyberapple.followme.repositories.ParticipantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExcursionService {
    private ExcursionRepository excursionRepository;

    private ParticipantRepository participantRepository;

    public Iterable<Excursion> getAllExcursions() {
        return this.excursionRepository.findAll();
    }

    public Excursion getExcursionById(String id) {
        // TODO: Add error handling here
        return this.excursionRepository.findById(id).orElse(null);
    }

    public void registerForExcursion(String id, Participant newParticipant) {
        newParticipant.setExcursion(this.getExcursionById(id));
        this.participantRepository.save(newParticipant);
    }
}
