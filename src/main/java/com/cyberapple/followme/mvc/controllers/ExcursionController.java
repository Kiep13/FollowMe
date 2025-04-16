package com.cyberapple.followme.mvc.controllers;

import com.cyberapple.followme.services.ExcursionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ExcursionController {

    private final ExcursionService excursionService;

    ExcursionController(ExcursionService excursionService) {
        this.excursionService = excursionService;
    }

    @GetMapping("/excursions")
    public String getDashboard(Model model) {
        var excursions = excursionService.getAllExcursions();

        model.addAttribute("excursions", excursions);
        return "dashboard";
    }

    @GetMapping("/excursions/{id}")
    public String getDashboard(@PathVariable String id, Model model) {
        var excursion = excursionService.getExcursionById(id);

        model.addAttribute("excursion", excursion);
        return "excursion";
    }
}
