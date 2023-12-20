package com.semantiq.server.service;

import com.semantiq.server.entity.BotData;
import com.semantiq.server.repository.BotDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotDataService {
    private final BotDataRepo botDataRepo;

    @Autowired
    public BotDataService(BotDataRepo botDataRepo) {
        this.botDataRepo = botDataRepo;
    }

    // Save bot data into database
    public void saveBotData(BotData botData) {
        botDataRepo.save(botData);
    }
}
