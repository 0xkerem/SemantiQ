package com.semantiq.server.service;

import com.semantiq.server.entity.Chat;
import com.semantiq.server.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Map<LocalDate, Long> getChatCountByDateRange(int botId, LocalDate startDate, LocalDate endDate) {
        return chatRepo.getChatCountByBotAndDateRange(botId, startDate, endDate);
    }

    public void setVote(int chatId, String vote) {
        Chat chat = findChatById(chatId);
        if (vote.equals("1")) chat.setVote("1");
        else if (vote.equals("-1")) chat.setVote("-1");

        chatRepo.save(chat);
    }
}
