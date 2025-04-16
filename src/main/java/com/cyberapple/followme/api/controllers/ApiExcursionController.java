package com.cyberapple.followme.api.controllers;

import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.services.ExcursionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiExcursionController {

    private final ExcursionService excursionService;

    public ApiExcursionController(ExcursionService excursionService) {
        this.excursionService = excursionService;
    }

    @GetMapping("api/excursions")
    public List<Excursion> getAllExcursions() {
        return excursionService.getAllExcursions();
    }
}
