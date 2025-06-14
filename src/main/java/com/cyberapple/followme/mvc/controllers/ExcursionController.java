package com.cyberapple.followme.mvc.controllers;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.services.ExcursionService;
import com.cyberapple.followme.exceptions.NotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

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
    public String getExcursionPage(@PathVariable String id, Model model) throws NotFoundException {
        ExcursionDto excursion = excursionService.getExcursionById(id);

        model.addAttribute("excursion", excursion);
        return "excursion";
    }

    // Deprecated
    // Not allowed by security anymore
    @GetMapping("/admin/excursions/new")
    public String getExcursionAddForm() {
        return "excursion-form";
    }

    // Deprecated
    // Not allowed by security anymore
    @PostMapping("/admin/excursions/new")
    public RedirectView saveNewExcursion() {
        return new RedirectView("excursions");
    }
}
