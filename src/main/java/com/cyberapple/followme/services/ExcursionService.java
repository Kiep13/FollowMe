package com.cyberapple.followme.services;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.entities.Participation;
import com.cyberapple.followme.repositories.ExcursionRepository;
import com.cyberapple.followme.repositories.ParticipationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExcursionService {
    private ExcursionRepository excursionRepository;

    private ParticipationRepository participationRepository;

    public Iterable<ExcursionDto> getAllExcursions() {
        return this.excursionRepository.findAllExcursionsWithAvailablePlaces();
    }

    public Excursion getExcursionById(String id) {
        // TODO: Add error handling here
        return this.excursionRepository.findById(id).orElse(null);
    }

    public void registerForExcursion(String id, Participation participation) {
        participation.setExcursion(this.excursionRepository.findById(id).orElse(null));

        // TODO: is it really good practise?
        participation.getParticipants().forEach(participant -> {
            participant.setParticipation(participation);
        });

        participationRepository.save(participation);
    }
}
