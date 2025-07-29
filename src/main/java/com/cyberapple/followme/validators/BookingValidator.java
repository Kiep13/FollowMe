package com.cyberapple.followme.validators;

import org.springframework.stereotype.Service;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.repositories.ExcursionRepository;

import lombok.AllArgsConstructor;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BookingValidator {
    private final ExcursionRepository excursionRepository;

    public void validateFreeSears(UUID excursionId, Integer amountOfParticipants) throws IllegalArgumentException {
        ExcursionDto excursion = excursionRepository.findExcursionWithAvailablePlacesById(excursionId).orElse(null);

        if (excursion.getAvailablePlaces() < amountOfParticipants) {
            throw new IllegalArgumentException("Not enough free seats for this excursion");
        }
    }
}
