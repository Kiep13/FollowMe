package com.cyberapple.followme.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cyberapple.followme.records.UserData;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;
    
    @Value("${jwt.secret.expiration-time}")
    private long expirationTime; // 1 hour

    public String generateToken(UserData userData) {
        return Jwts.builder()
                .subject(userData.email())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }
}
