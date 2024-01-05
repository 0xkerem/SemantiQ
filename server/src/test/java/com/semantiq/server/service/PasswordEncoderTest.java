package com.semantiq.server.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncoderTest {

    @Test
    public void testEncodePassword() {
        // Arrange
        String rawPassword = "myPassword";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Act
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Assert
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}

