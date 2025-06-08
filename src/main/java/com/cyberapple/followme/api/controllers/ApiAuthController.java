package com.cyberapple.followme.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cyberapple.followme.repositories.UserRepository;
import com.cyberapple.followme.services.JwtService;
import com.cyberapple.followme.entities.User;
import com.cyberapple.followme.exceptions.InvalidCredentialsException;
import com.cyberapple.followme.records.AuthResponse;
import com.cyberapple.followme.records.LoginRequest;
import com.cyberapple.followme.records.UserData;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class ApiAuthController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new InvalidCredentialsException("User not found with email: " + loginRequest.email()));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        UserData userData = new UserData(user);
        String token = jwtService.generateToken(userData);

        return new AuthResponse(user, token);
    }
}
