package com.cyberapple.followme.security;

import com.cyberapple.followme.security.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfigurer extends AbstractHttpConfigurer<JwtSecurityConfigurer, HttpSecurity> {

    private final DatabaseAuthenticationProvider databaseAuthenticationProvider;

    @Override
    public void init(HttpSecurity http) throws Exception {
        http.authenticationProvider(databaseAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        var authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
    }
}
