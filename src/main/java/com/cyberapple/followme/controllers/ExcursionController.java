package com.cyberapple.followme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExcursionController {

    @GetMapping("/")
    public String greeting() {
        return "dashboard";
    }
}
