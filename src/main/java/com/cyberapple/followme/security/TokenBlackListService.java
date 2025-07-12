package com.cyberapple.followme.security;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TokenBlackListService {
    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();
    
    public void addTokenToBlacklist(String token) {
      blacklistedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
      return blacklistedTokens.contains(token);
    }

    public void flushBlacklist() {
      blacklistedTokens.clear();
    }
}