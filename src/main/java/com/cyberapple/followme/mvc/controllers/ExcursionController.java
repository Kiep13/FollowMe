package com.cyberapple.followme.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExcursionController {

    @GetMapping("excursions")
    public String greeting() {
        return "dashboard";
    }
}
