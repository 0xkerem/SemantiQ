package com.semantiq.server.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.semantiq.server.entity.ChatBot;
import com.semantiq.server.repository.ChatBotRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChatBotRepoTest {

    @Mock
    private ChatBotRepo chatBotRepoMock;

    @Test
    public void testFindById() {
        // Create a chatbot
        ChatBot chatBot = new ChatBot();
        chatBot.setId(1);

        // Mock the findById method behavior using Mockito
        when(chatBotRepoMock.findById(1)).thenReturn(chatBot);

        // Find the chatbot by id
        ChatBot foundChatBot = chatBotRepoMock.findById(1);

        // Did it return the expected chatbot?
        assertEquals(1, foundChatBot.getId());
    }
}


