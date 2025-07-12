package com.cyberapple.followme.configuration;

import com.cyberapple.followme.security.DatabaseAuthenticationProvider;
import com.cyberapple.followme.security.JwtSecurityConfigurer;
import com.cyberapple.followme.repositories.UserRepository;
import com.cyberapple.followme.services.CustomUserDetailsService;
import com.cyberapple.followme.security.TokenBlackListService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private TokenBlackListService tokenBlackListService;

    @Bean 
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    DatabaseAuthenticationProvider databaseAuthenticationProvider(UserDetailsService userDetailsService) {
        return new DatabaseAuthenticationProvider(userDetailsService, tokenBlackListService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successListener() {
        return event -> {
            System.out.println("Authentication successful for user: " + event.getAuthentication().getName());
        };
    }

    @Bean 
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DatabaseAuthenticationProvider databaseAuthenticationProvider,
            AuthenticationEventPublisher authenticationEventPublisher
    ) throws Exception {
//        var authenticationManager = new ProviderManager(databaseAuthenticationProvider);
//        authenticationManager.setAuthenticationEventPublisher(authenticationEventPublisher);

        var jwtSecurityConfigurer = new JwtSecurityConfigurer(databaseAuthenticationProvider);

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
            .with(jwtSecurityConfigurer, Customizer.withDefaults())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .permitAll()
            );
        
        return http.build();
    }
}