package com.cyberapple.followme.services;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.dtos.PriceRange;
import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.records.ExcursionInput;
import com.cyberapple.followme.repositories.ExcursionRepository;
import com.cyberapple.followme.validators.ExcursionIdValidator;
import com.cyberapple.followme.exceptions.NotFoundException;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExcursionService {
    private final ExcursionRepository excursionRepository;
    private final ExcursionIdValidator excursionIdValidator;

    public Iterable<ExcursionDto> getAllExcursions() {
        return this.excursionRepository.findAllExcursionsWithAvailablePlaces();
    }

    public PriceRange getPriceRange() {
        return this.excursionRepository.getPriceRange();
    }

    public ExcursionDto getExcursionById(String id) throws NotFoundException {
        excursionIdValidator.validateExcursionId(id);

        return this.excursionRepository.findExcursionWithAvailablePlacesById(id)
                .orElseThrow(() -> new NotFoundException("Excursion not found with id: " + id));
    }

    public void createExcursion(ExcursionInput excursionInput) {
        Excursion excursion = new Excursion(excursionInput);
        this.excursionRepository.save(excursion);
    }
}
