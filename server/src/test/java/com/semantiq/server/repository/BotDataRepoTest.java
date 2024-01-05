package com.semantiq.server.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.semantiq.server.entity.BotData;
import com.semantiq.server.repository.BotDataRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BotDataRepoTest {

    @Mock
    private BotDataRepo botDataRepoMock;

    @Test
    public void testSaveBotData() {
        // Create a new BotData entity
        BotData botData = new BotData();
        botData.setId(1);

        // Call the save method
        botDataRepoMock.save(botData);

        // Verify that the save method was called with the correct BotData entity
        verify(botDataRepoMock, times(1)).save(botData);
    }

    @Test
    public void testDeleteBotData() {
        // Create a BotData entity
        BotData botData = new BotData();
        botData.setId(1);

        // Call the delete method
        botDataRepoMock.delete(botData);

        // Verify that the delete method was called with the correct BotData entity
        verify(botDataRepoMock, times(1)).delete(botData);
    }
}

