package com.cyberapple.followme.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cyberapple.followme.repositories.UserRepository;
import com.cyberapple.followme.entities.User;
import com.cyberapple.followme.exceptions.InvalidCredentialsException;
import com.cyberapple.followme.records.LoginRequest;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class ApiAuthController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new InvalidCredentialsException("User not found with email: " + loginRequest.email()));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return user;
    }
}
