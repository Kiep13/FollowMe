package com.cyberapple.followme.records;

import com.cyberapple.followme.entities.User;

import java.util.UUID;

public record UserData(UUID id, String firstName, String lastName, String email, String role) {
    public UserData(User user) {
        this(user.getId(), 
             user.getFirstName(), 
             user.getLastName(), 
             user.getEmail(), 
             user.getRole());
    }

}
