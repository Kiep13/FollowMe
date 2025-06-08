package com.cyberapple.followme.services;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.dtos.PriceRange;
import com.cyberapple.followme.repositories.ExcursionRepository;

import lombok.AllArgsConstructor;
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

    public ExcursionDto getExcursionById(String id) {
        // TODO: Add error handling here
        return this.excursionRepository.findExcursionWithAvailablePlacesById(id).orElse(null);
    }
}
