package com.cyberapple.followme.api.controllers;

import com.cyberapple.followme.repositories.ExcursionRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/countries")
@AllArgsConstructor
public class ApiCountryController {
    private ExcursionRepository excursionRepository;

    @GetMapping("/list")
    public Iterable<String> getCountryList() {
        return excursionRepository.getCountryList();
    }
}
