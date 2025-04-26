package com.cyberapple.followme.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.services.ExcursionService;

@RestController
public class ApiExcursionController {

    private final ExcursionService excursionService;

    public ApiExcursionController(ExcursionService excursionService) {
        this.excursionService = excursionService;
    }

    @GetMapping("api/excursions")
    public Iterable<Excursion> getAllExcursions() {
        return excursionService.getAllExcursions();
    }

    @GetMapping("api/excursions/{id}")
    public Excursion getExcursionById(@PathVariable String id) {
        return excursionService.getExcursionById(id);
    }
}
