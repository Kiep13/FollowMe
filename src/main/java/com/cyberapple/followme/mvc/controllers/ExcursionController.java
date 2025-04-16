package com.cyberapple.followme.mvc.controllers;

import com.cyberapple.followme.services.ExcursionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
