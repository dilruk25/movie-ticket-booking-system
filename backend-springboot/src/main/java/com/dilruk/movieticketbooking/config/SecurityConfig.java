package com.dilruk.movieticketbooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Used basic auth to know the user who sent the request
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/landing/**").permitAll()
                                .requestMatchers("/api/super/**").hasRole("ADMIN")
                                .requestMatchers("/api/vendor/**").hasRole("VENDOR")
                                .requestMatchers("/api/v1/tickets/**").hasRole("VENDOR")
                                .requestMatchers("/api/v1/movies/**").hasRole("VENDOR")
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}