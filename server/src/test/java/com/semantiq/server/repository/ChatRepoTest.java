package com.semantiq.server.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.semantiq.server.entity.Chat;
import com.semantiq.server.repository.ChatRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class ChatRepoTest {

    @Mock
    private ChatRepo chatRepoMock;

    @Test
    public void testFindById() {
        // Create chat data
        Chat chat = new Chat();
        chat.setId(1);

        // Mock the findById method behavior using Mockito
        when(chatRepoMock.findById(1)).thenReturn(chat);

        // Find the chat by id
        Chat foundChat = chatRepoMock.findById(1);

        // Did it return the expected chat?
        assertEquals(1, foundChat.getId());
    }

    @Test
    public void testFindAllByBotId() {
        // Mocking the findAllByBotId method
        when(chatRepoMock.findAllByBotId(1)).thenReturn(List.of(new Chat(), new Chat()));

        // Retrieve chats by botId
        List<Chat> chatList = chatRepoMock.findAllByBotId(1);

        // Ensure that the correct number of chats is retrieved
        assertEquals(2, chatList.size());
    }

    @Test
    public void testFindChatsCountLast15Days() {
        // Mocking the findChatsCountLast15Days method
        when(chatRepoMock.findChatsCountLast15Days(1, LocalDate.now(), LocalDate.now())).thenReturn(List.of(Map.of("date", LocalDate.now(), "totalUsage", 5)));

        // Retrieve chat count for the last 15 days
        List<Map<String, Object>> chatCountList = chatRepoMock.findChatsCountLast15Days(1, LocalDate.now(), LocalDate.now());

        // Ensure that the correct chat count data is retrieved
        assertEquals(1, chatCountList.size());
        assertEquals(LocalDate.now(), chatCountList.get(0).get("date"));
        assertEquals(5, chatCountList.get(0).get("totalUsage"));
    }
}

