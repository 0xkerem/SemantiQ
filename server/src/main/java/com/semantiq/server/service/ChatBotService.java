package com.semantiq.server.service;

import com.semantiq.server.entity.ChatBot;
import com.semantiq.server.repository.ChatBotRepo;
import org.springframework.stereotype.Service;

@Service
public class ChatBotService {
    private final ChatBotRepo chatBotRepo;

    public ChatBotService(ChatBotRepo chatBotRepo) {
        this.chatBotRepo = chatBotRepo;
    }

    public void saveChatBot(ChatBot chatBot) {
        chatBotRepo.save(chatBot);
    }
}
