package com.cyberapple.followme.api.controllers;

import lombok.AllArgsConstructor;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cyberapple.followme.dtos.PriceRange;
import com.cyberapple.followme.records.ExcursionInput;
import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.services.ExcursionService;

@RestController
@RequestMapping("api/excursions")
@AllArgsConstructor
public class ApiExcursionController {

    private final ExcursionService excursionService;

    @GetMapping("")
    public Iterable<ExcursionDto> getAllExcursions() {
        return excursionService.getAllExcursions();
    }

    @GetMapping("/price-range")
    public PriceRange getPriceRange() {
        return excursionService.getPriceRange();
    }

    @PostMapping("/add")
    @PostAuthorize("hasRole('ADMIN')")
    public void createExcursion(@RequestBody ExcursionInput excursionInput) {
        excursionService.createExcursion(excursionInput);
    }

    @GetMapping("/{id}")
    public ExcursionDto getExcursionById(@PathVariable String id) {
        return excursionService.getExcursionById(id);
    }
}
