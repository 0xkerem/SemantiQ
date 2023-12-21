package com.semantiq.server.service;

import com.semantiq.server.entity.BotData;
import com.semantiq.server.entity.ChatBot;
import com.semantiq.server.repository.BotDataRepo;
import com.semantiq.server.repository.ChatBotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BotDataService {
    private final BotDataRepo botDataRepo;

    @Autowired
    public BotDataService(BotDataRepo botDataRepo, ChatBotRepo chatBotRepo) {
        this.botDataRepo = botDataRepo;}

    // Save bot data into database
    public void saveBotData(BotData botData) {
        botDataRepo.save(botData);
    }

}
