package com.example.dms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Разрешить всем доступ
                )
                .csrf(csrf -> csrf.disable()) // Отключить CSRF-защиту (если нужно)
                .formLogin(login -> login.disable()) // Отключить форму входа
                .httpBasic(basic -> basic.disable()); // Отключить Basic Auth

        return http.build();
    }
}
