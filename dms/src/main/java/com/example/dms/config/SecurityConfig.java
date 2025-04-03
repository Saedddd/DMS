package com.example.dms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for REST API
                .authorizeHttpRequests(authorize -> authorize
                        // Swagger UI
                        .requestMatchers("/swagger-ui/**",
                                         "/swagger-ui.html", 
                                         "/v3/api-docs/**",
                                         "/api-docs/**").permitAll()
                        // Actuator endpoints
                        .requestMatchers("/actuator/**").permitAll()
                        // All other requests
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
