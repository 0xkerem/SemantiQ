package com.semantiq.server.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.semantiq.server.entity.User;
import com.semantiq.server.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRepoTest {

    @Mock
    private UserRepo userRepoMock;

    @Test
    public void testSaveUser() {
        // Create a new user
        User user = new User();
        user.setId(1);
        user.setEmail("example@email.com");

        // Call the save method
        userRepoMock.save(user);

        // Was the user really saved to the database?
        verify(userRepoMock, times(1)).save(user);
    }

    @Test
    public void testFindUserById() {
        // Create user data
        User user = new User();
        user.setId(1);
        user.setEmail("example@email.com");

        // Mock the findById method behavior using Mockito
        when(userRepoMock.findById(1)).thenReturn(user);

        // Find the user by id
        User foundUser = userRepoMock.findById(1);

        // Did it return the expected user?
        assertEquals("example@email.com", foundUser.getEmail());
        assertEquals(1, foundUser.getId());
    }

    @Test
    public void testDeleteUser() {
        // Create user data
        User user = new User();
        user.setId(1);
        user.setEmail("example@email.com");

        // Call the delete method
        userRepoMock.delete(user);

        // Was the user really deleted from the database?
        verify(userRepoMock, times(1)).delete(user);
    }
}

