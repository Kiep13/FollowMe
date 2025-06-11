package com.cyberapple.followme.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cyberapple.followme.classes.ExtendedUserDetails;
import com.cyberapple.followme.entities.User;

@Service
public class AuthenticationService {
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ExtendedUserDetails userDetails = (ExtendedUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
