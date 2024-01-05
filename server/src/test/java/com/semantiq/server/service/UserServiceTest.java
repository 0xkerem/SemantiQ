package com.semantiq.server.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.semantiq.server.entity.ChatBot;
import com.semantiq.server.entity.User;
import com.semantiq.server.repository.UserRepo;
import com.semantiq.server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;



    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindUserByEmail() {
        // Arrange
        String email = "BruceWayne@example.com";
        User user = new User();
        user.setEmail(email);
        when(userRepo.findByEmail(email)).thenReturn(user);

        // Act
        User foundUser = userService.findUserByEmail(email);

        // Assert
        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
    }

    @Test
    public void testFindUserByEmail_NotFound() {
        // Arrange
        String email = "unknown@example.com";
        when(userRepo.findByEmail(email)).thenReturn(null);

        // Act
        User foundUser = userService.findUserByEmail(email);

        // Assert
        assertNull(foundUser);
    }

    @Test
    public void testFindUserById() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setId(userId);
        when(userRepo.findById(userId)).thenReturn(user);

        // Act
        User foundUser = userService.findUserById(userId);

        // Assert
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    public void testFindUserById_NotFound() {
        // Arrange
        int id = 999; // varsayılan bir id
        when(userRepo.findById(id)).thenReturn(null);

        // Act
        User foundUser = userService.findUserById(id);

        // Assert
        assertNull(foundUser);
    }

    @Test
    public void testSaveUser() {
        // Arrange
        User userToSave = new User("Bruce", "Wayne", "Wayne Enterprises", "BruceWayne@example.com", "CapedCrus4der");

        // Act
        userService.saveUser(userToSave);

        // Assert
        verify(userRepo, times(1)).save(userToSave);
    }

    @Test
    public void testVerifyUser_WhenVerificationCodeMatches() {
        // Arrange
        String email = "cmc23@example.com";
        int verificationCode = 1234;
        User user = new User("Christian", "McCaffrey", "NFL", email, "CMC_23-Rb");
        user.setVerificationCode(verificationCode);

        when(userRepo.findByEmail(email)).thenReturn(user);

        // Act
        boolean result = userService.verifyUser(email, verificationCode);

        // Assert
        assertTrue(result);
        assertTrue(user.isVerified());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void testVerifyUser_WhenVerificationCodeDoesNotMatch() {
        // Arrange
        String email = "barry_allen@example.com";
        int originalVerificationCode = 1234;
        int invalidVerificationCode = 4321;
        User user = new User("Barry", "Allen", "Star Labs", email, "7heFlash");
        user.setVerificationCode(originalVerificationCode);

        when(userRepo.findByEmail(email)).thenReturn(user);

        // Act
        boolean result = userService.verifyUser(email, invalidVerificationCode);

        // Assert
        assertFalse(result);
        assertFalse(user.isVerified());
        verify(userRepo, never()).save(user);
    }

    @Test
    public void testDeleteBot_WhenUserHasNoBot() {
        // Arrange
        String userEmail = "test@example.com";
        User userWithoutBot = new User();
        userWithoutBot.setEmail(userEmail);
        userWithoutBot.setBot(null);

        when(userRepo.findByEmail(userEmail)).thenReturn(userWithoutBot);

        // Act
        boolean result = userService.deleteBot(userEmail);

        // Assert
        assertFalse(result);
        verify(userRepo, never()).save(userWithoutBot);
    }

    @Test
    public void testDeleteBot_WhenUserHasBot() {
        // Arrange
        String userEmail = "test@example.com";
        User userWithBot = new User();
        userWithBot.setEmail(userEmail);
        userWithBot.setBot(new ChatBot());

        when(userRepo.findByEmail(userEmail)).thenReturn(userWithBot);

        // Act
        boolean result = userService.deleteBot(userEmail);

        // Assert
        assertTrue(result);
        assertNull(userWithBot.getBot());
        verify(userRepo, times(1)).save(userWithBot);
    }

    @Test
    public void testDeleteBot_WhenUserHasBot_DeletionFailed() {
        // Arrange
        String userEmail = "tes@example.com";
        User userWithBot = new User();
        userWithBot.setEmail(userEmail);
        userWithBot.setBot(new ChatBot());

        when(userRepo.findByEmail(userEmail)).thenReturn(userWithBot);
        doThrow(new RuntimeException("Bot deletion failed")).when(userRepo).save(any(User.class));

        // Act
        boolean result1 = userService.deleteBot(userEmail);

        // Assert
        assertFalse(result1);
        assertNotNull(userWithBot.getBot());
        verify(userRepo, times(1)).save(userWithBot);
    }







}

