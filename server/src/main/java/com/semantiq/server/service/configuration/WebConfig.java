package com.semantiq.server.service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // CORS configuration for /api/bots/**
        registry.addMapping("/api/bots/")
                .allowedOriginPatterns("*") // Use allowedOriginPatterns instead of allowedOrigins
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // Add more configurations if needed
                .allowCredentials(true);

        // CORS configuration for /api/users/**
        registry.addMapping("/api/users/")
                .allowedOriginPatterns("*") // Use allowedOriginPatterns instead of allowedOrigins
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // Add more configurations if needed
                .allowCredentials(true);

        // CORS configuration for /static/**
        registry.addMapping("/static/")
                .allowedOriginPatterns("*") // Use allowedOriginPatterns instead of allowedOrigins
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // Add more configurations if needed
                .allowCredentials(true);

        // CORS configuration for /static/**
        registry.addMapping("/api/botdata/")
                .allowedOriginPatterns("*") // Use allowedOriginPatterns instead of allowedOrigins
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // Add more configurations if needed
                .allowCredentials(true);

        // CORS configuration for /static/**
        registry.addMapping("/api/email/")
                .allowedOriginPatterns("*") // Use allowedOriginPatterns instead of allowedOrigins
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // Add more configurations if needed
                .allowCredentials(true);
    }
}

