package com.cyberapple.followme.services;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.dtos.PriceRange;
import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.records.ExcursionInput;
import com.cyberapple.followme.repositories.ExcursionRepository;
import com.cyberapple.followme.exceptions.NotFoundException;

import lombok.AllArgsConstructor;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExcursionService {
    private final ExcursionRepository excursionRepository;

    public Iterable<ExcursionDto> getAllExcursions() {
        return this.excursionRepository.findAllExcursionsWithAvailablePlaces();
    }

    public PriceRange getPriceRange() {
        return this.excursionRepository.getPriceRange();
    }

    public ExcursionDto getExcursionById(String id) throws NotFoundException {
        if (id == null || id.isEmpty()) {
            throw new NotFoundException("Excursion id cannot be null or empty");
        }

        try {
            UUID.fromString(id); 
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Invalid format for excursion id: " + id);
        }

        if(excursionRepository.existsById(id) == false) {
            throw new NotFoundException("Excursion not found with id: " + id);
        }

        return this.excursionRepository.findExcursionWithAvailablePlacesById(id)
                .orElseThrow(() -> new NotFoundException("Excursion not found with id: " + id));
    }

    public void createExcursion(ExcursionInput excursionInput) {
        Excursion excursion = new Excursion(excursionInput);
        this.excursionRepository.save(excursion);
    }
}
