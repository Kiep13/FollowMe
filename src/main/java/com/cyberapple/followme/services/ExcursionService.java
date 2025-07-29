package com.cyberapple.followme.services;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.dtos.ExcursionParticipantsDto;
import com.cyberapple.followme.dtos.PriceRange;
import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.entities.Participant;
import com.cyberapple.followme.entities.Participation;
import com.cyberapple.followme.models.ExcursionSearch;
import com.cyberapple.followme.records.ExcursionInput;
import com.cyberapple.followme.repositories.ExcursionRepository;
import com.cyberapple.followme.repositories.ParticipationRepository;
import com.cyberapple.followme.validators.ExcursionValidator;
import com.cyberapple.followme.exceptions.NotFoundException;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExcursionService {
    private final ExcursionRepository excursionRepository;
    private final ParticipationRepository participationRepository;

    private final ExcursionValidator excursionIdValidator;

    public Iterable<ExcursionDto> getAllExcursions() {
        return this.excursionRepository.findAllExcursionsWithAvailablePlaces();
    }

    public Iterable<ExcursionDto> searchExcursions(ExcursionSearch excursionSearch, Pageable pageable) {
        return this.excursionRepository.searchExcursions(
                excursionSearch.getMinPrice(),
                excursionSearch.getMaxPrice(),
                List.of(excursionSearch.getCountries()),
                excursionSearch.getStartDate(),
                excursionSearch.getEndDate(),
                excursionSearch.isHasAvailableSeats(),
                excursionSearch.isActive(),
                pageable
        );
    }

    public PriceRange getPriceRange() {
        return this.excursionRepository.getPriceRange();
    }

    public ExcursionDto getExcursionById(String id) throws NotFoundException {
        var uuidExcursionId = excursionIdValidator.validateExcursionId(id);

        return this.excursionRepository.findExcursionWithAvailablePlacesById(uuidExcursionId)
                .orElseThrow(() -> new NotFoundException("Excursion not found with id: " + id));
    }

    public void createExcursion(ExcursionInput excursionInput) {
        Excursion excursion = new Excursion(excursionInput);
        this.excursionRepository.save(excursion);
    }

    public ExcursionParticipantsDto getExcursionParticipants(String id) throws NotFoundException {
        var uuidExcursionId = excursionIdValidator.validateExcursionId(id);

        ExcursionDto excursion = this.excursionRepository.findExcursionWithAvailablePlacesById(uuidExcursionId)
                .orElseThrow(() -> new NotFoundException("Excursion not found with id: " + id));

        Iterable<Participation> participations = this.participationRepository.findByExcursionId(excursion.getId());
        List<Participant> participants = new ArrayList<>();

        for (Participation participation : participations) {
            participants.addAll(participation.getParticipants());
        }

        return new ExcursionParticipantsDto(excursion, participants);
    }

    public void updateExcursionPlaces(String id, int amountOfPlaces) throws NotFoundException {
        var uuidExcursionId = excursionIdValidator.validateExcursionId(id);

        excursionRepository.updateExcursionPlaces(uuidExcursionId, amountOfPlaces);
    }
}
