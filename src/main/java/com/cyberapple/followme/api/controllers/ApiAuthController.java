package com.cyberapple.followme.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cyberapple.followme.repositories.UserRepository;
import com.cyberapple.followme.entities.User;
import com.cyberapple.followme.exceptions.InvalidCredentialsException;
import com.cyberapple.followme.records.LoginRequest;

@RestController
@AllArgsConstructor
public class ApiAuthController {
    private final UserRepository userRepository;

    @GetMapping("api/auth/login")
    public User login(@RequestBody LoginRequest loginRequest) {
        return userRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
    }
}
