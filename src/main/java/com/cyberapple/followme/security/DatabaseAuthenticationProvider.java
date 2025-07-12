package com.cyberapple.followme.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@RequiredArgsConstructor
public class DatabaseAuthenticationProvider  implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;

    private final TokenBlackListService tokenBlackListService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public Authentication authenticate(Authentication authentication) {
        String token = (String) authentication.getCredentials();

        try {
            if(tokenBlackListService.isTokenBlacklisted(token)) {
                SecurityContextHolder.clearContext();
                return null;
            }

            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String username = claims.getSubject();

            if (username == null) {
                return null;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken userAuthentication = UsernamePasswordAuthenticationToken.authenticated(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(userAuthentication);

            return userAuthentication;
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
