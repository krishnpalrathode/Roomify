package com.hotelmanagement.roomify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/Images/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Images/**").permitAll())
                .csrf(csrf -> csrf.disable());

        return http.build();

    }
}
