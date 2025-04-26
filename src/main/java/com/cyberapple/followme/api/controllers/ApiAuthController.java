package com.cyberapple.followme.api.controllers;

import com.cyberapple.followme.records.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cyberapple.followme.repositories.UserRepository;
import com.cyberapple.followme.entities.User;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class ApiAuthController {
    private final UserRepository userRepository;

    @GetMapping("api/auth/login")
    public Optional<User> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password());

        if (user.isPresent()) {
            return user;
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
