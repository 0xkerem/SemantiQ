<<<<<<< HEAD
package com.semantiq.server.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordCheckTest {

    @Test
    public void testCheckPassword() {
        // Arrange
        String rawPassword = "myPassword";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Act
        boolean passwordMatches = passwordEncoder.matches(rawPassword, encodedPassword);

        // Assert
        assertTrue(passwordMatches);
    }

    @Test
    public void testCheckPassword_NonMatchingPasswords() {
        // Arrange
        String rawPassword = "myPassword";
        String differentPassword = "anotherPassword";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Act & Assert
        assertFalse(passwordEncoder.matches(differentPassword, encodedPassword));
    }
}

=======
package com.semantiq.server.service;public class PasswordCheckTest {
}
>>>>>>> origin/main
