package com.post.blog.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${api.security.token}")
    private String apiToken;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        if (apiToken == null || apiToken.isEmpty()) {
            throw new IllegalArgumentException("API token must be configured");
        }

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new ApiTokenFilter(apiToken), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}