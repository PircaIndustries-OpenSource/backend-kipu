package com.kipu.backend.shared.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class PublicEndpointsSecurityConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain publicEndpointsFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/v1/material-categories/**",
                        "/api/v1/material-catalogs/**",
                        "/api/v1/material-inventories/**",
                        "/api/v1/material-requests/**",
                        "/api/v1/documents/**",
                        "/api/v1/team-users/**",
                        "/api/v1/team-workers/**",
                        "/api/v1/suppliers/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}
