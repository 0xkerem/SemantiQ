package com.semantiq.server.service;

import com.semantiq.server.entity.Chat;
import com.semantiq.server.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {
    private final ChatRepo chatRepo;

    @Autowired
    public ChatService(ChatRepo chatRepo) {
        this.chatRepo = chatRepo;
    }

    public Chat findChatById(int id) {
        return chatRepo.findById(id);
    }

    public List<Chat> findAllChatsForBot(int botId) {
        return chatRepo.findAllByBotId(botId);
    }

    public List<Chat> findAllChatsForBotFromLastYear(int botId) {
        // Calculate the date one year ago from the current date
        LocalDateTime oneYearAgo = LocalDateTime.now().minus(1, ChronoUnit.YEARS);

        // Retrieve chats from the last year for the given botId
        return chatRepo.findAllByBotIdAndCreatedAtAfter(botId, oneYearAgo);
    }

    public void setVote(int chatId, int vote) {
        Chat chat = findChatById(chatId);
        chat.setVote(String.valueOf(vote)); // Convert int to String

        chatRepo.save(chat);
    }

    public List<Map<String, Object>> getChatsCountLast15Days(int chatBotId) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(14); // 15 days ago

        return chatRepo.findChatsCountLast15Days(chatBotId, startDate, endDate);
    }
}
