package com.cyberapple.followme.configuration;

import com.cyberapple.followme.security.filters.JwtAuthenticationFilter;
import com.cyberapple.followme.repositories.UserRepository;
import com.cyberapple.followme.services.CustomUserDetailsService;
import com.cyberapple.followme.security.TokenBlackListService;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
    @Bean 
    public TokenBlackListService tokenBlackListService() {
        return new TokenBlackListService();
    }

    @Bean 
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean JwtAuthenticationFilter jwtAuthenticationFilter(UserDetailsService userDetailsService, TokenBlackListService tokenBlackListService) {
        return new JwtAuthenticationFilter(userDetailsService, tokenBlackListService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(authorize -> authorize
                // Web MVC pages
                .requestMatchers("/excursions", "/excursions/*", "/login").permitAll() 
                // Rest API endpoints
                .requestMatchers("/api/**", "/login").permitAll() 
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
                .anyRequest().authenticated() 
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(logout -> logout
                .logoutUrl("/logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());
        
        return http.build();
    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}