package com.cyberapple.followme.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.entities.Participation;
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

    @GetMapping("/{id}")
    public ExcursionDto getExcursionById(@PathVariable String id) {
        return excursionService.getExcursionById(id);
    }

    @PostMapping("/{id}/join")
    public void registerForExcursion(@PathVariable String id, @RequestBody Participation participation) {
        excursionService.registerForExcursion(id, participation);
    }
}
