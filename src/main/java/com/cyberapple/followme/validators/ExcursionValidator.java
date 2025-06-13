package com.cyberapple.followme.validators;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cyberapple.followme.exceptions.NotFoundException;
import com.cyberapple.followme.repositories.ExcursionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExcursionValidator {
    private final ExcursionRepository excursionRepository;

    public boolean validateExcursionId(String id) throws NotFoundException {
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

        return true;
    }
}
