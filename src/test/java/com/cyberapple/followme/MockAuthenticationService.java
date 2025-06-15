package com.cyberapple.followme;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.cyberapple.followme.entities.User;
import com.cyberapple.followme.services.AuthenticationService;

@Profile("test")
@Primary
@Service
public class MockAuthenticationService extends AuthenticationService {
    
    private User mockUser;
    
    @Override
    public User getAuthenticatedUser() {
        return mockUser;
    }
    
    public void setMockUser(User user) {
        this.mockUser = user;
    }
}