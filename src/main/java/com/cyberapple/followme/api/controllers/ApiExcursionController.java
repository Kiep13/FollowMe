package com.cyberapple.followme.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.cyberapple.followme.entities.Excursion;
import com.cyberapple.followme.entities.Participant;
import com.cyberapple.followme.services.ExcursionService;

import java.util.List;

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

    @PostMapping("api/excursions/{id}/join")
    public void registerForExcursion(@PathVariable String id, @RequestBody List<Participant> newParticipants) {
        excursionService.registerForExcursion(id, newParticipants);
    }
}
