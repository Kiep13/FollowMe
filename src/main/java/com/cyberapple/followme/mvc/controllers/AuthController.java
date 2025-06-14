package com.cyberapple.followme.mvc.controllers;

import com.cyberapple.followme.entities.User;
import com.cyberapple.followme.exceptions.InvalidCredentialsException;
import com.cyberapple.followme.repositories.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    String processLogin(Model model, @RequestParam("email") String email, @RequestParam("password") String password) {
        Optional<User> user = userRepository.findByEmail(email);

        System.out.println(user.get());
        System.out.println(passwordEncoder.matches(password, user.get().getPassword()));
        
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            model.addAttribute("errorMessage", "Invalid email or password");
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            return "login";
        }

        model.addAttribute("user", user.get());
        return "redirect:/excursions";
    }
}
