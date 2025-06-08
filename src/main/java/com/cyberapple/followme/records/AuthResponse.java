package com.cyberapple.followme.records;

import com.cyberapple.followme.entities.User;

public record AuthResponse(User user, String token) {
}
