package com.cyberapple.followme.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cyberapple.followme.repositories.UserRepository;
import com.cyberapple.followme.services.JwtService;
import com.cyberapple.followme.services.TokenBlackListService;

import io.micrometer.core.annotation.Counted;
import jakarta.validation.Valid;

import com.cyberapple.followme.entities.User;
import com.cyberapple.followme.exceptions.InvalidCredentialsException;
import com.cyberapple.followme.records.AuthResponse;
import com.cyberapple.followme.records.LoginRequest;
import com.cyberapple.followme.records.UserData;
import com.cyberapple.followme.records.UserInput;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class ApiAuthController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final TokenBlackListService tokenBlackListService;

    @PostMapping("/login")
    @Counted(value = "api.calls.login", description = "Number of calls to /login")
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

    @PostMapping("/register")
    public void register(@Valid @RequestBody UserInput userInput) {
        if (userRepository.existsByEmail(userInput.email())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User user = new User(userInput);
        user.setPassword(passwordEncoder.encode(userInput.password()));

        userRepository.save(user);
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public void logout(@RequestHeader("Authorization") String token) {
        String cleanToken = token.replace("Bearer ", "");
        tokenBlackListService.addTokenToBlacklist(cleanToken);
    }

    @GetMapping("users")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
