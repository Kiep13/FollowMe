package com.cyberapple.followme.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.services.ExcursionService;

@RestController
@AllArgsConstructor
public class ApiExcursionController {

    private final ExcursionService excursionService;

    @GetMapping("api/excursions")
    public Iterable<Excursion> getAllExcursions() {
        return excursionService.getAllExcursions();
    }

    @GetMapping("api/excursions/{id}")
    public Excursion getExcursionById(@PathVariable String id) {
        return excursionService.getExcursionById(id);
    }
}
