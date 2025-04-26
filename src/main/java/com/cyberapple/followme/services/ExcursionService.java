package com.cyberapple.followme.services;

import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.repositories.ExcursionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExcursionService {
    private ExcursionRepository excursionRepository;

    public Iterable<Excursion> getAllExcursions() {
        return this.excursionRepository.findAll();
    }

    public Excursion getExcursionById(String id) {
        // TODO: Add error handling here
        return this.excursionRepository.findById(id).orElse(null);
    }
}
