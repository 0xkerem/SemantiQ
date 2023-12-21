package com.semantiq.server.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.semantiq.server.DTO.VoteDto;
import com.semantiq.server.entity.BotData;
import com.semantiq.server.entity.ChatBot;
import com.semantiq.server.service.BotDataService;
import com.semantiq.server.service.ChatBotService.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/botdata")
public class BotDataController {
    private final BotDataService botDataService;
    private final ChatBotService chatBotService;

    @Autowired
    public BotDataController(BotDataService botDataService, ChatBotService chatBotService) {
        this.botDataService = botDataService;
        this.chatBotService = chatBotService;
    }

    @PostMapping("/upvote/{chatBotId}")
    // countPos'u bir arttır (parametre chatbotun id'si)
    // BotDataService'in içinde de implemente edilip burada fonskiyon gibi çağırılabilir.
    // yorumları sil
    // yapılan arttırmalar veritabanına kaydeilmesi için işlem bitince servisin içindeki saveBotData kullanılmalı
    public ResponseEntity<?> upvoteBot(@PathVariable int chatBotId){
        ChatBot chatBot = chatBotService.findChatBotById(chatBotId);
        if (chatBot == null) {
            return new ResponseEntity<>("ChatBot not found", HttpStatus.NOT_FOUND);
        }

        BotData botData = chatBot.getData();

        if (botData == null){
            return new ResponseEntity<>("BotData not found", HttpStatus.NOT_FOUND);
        }
        int botDataCountPos = botData.getCountPos() + 1;

        botData.setCountPos(botDataCountPos);
        botDataService.saveBotData(botData);
        return new ResponseEntity<>("ChatBot upvote", HttpStatus.OK);
    }
    @PostMapping("/downvote/{chatBotId}")
    // countNeg'i bir arttır (parametre chatbotun id'si)
    public ResponseEntity<?> downvoteBot(@PathVariable int chatBotId){
        ChatBot chatBot = chatBotService.findChatBotById(chatBotId);
        if (chatBot == null) {
            return new ResponseEntity<>("ChatBot not found", HttpStatus.NOT_FOUND);
        }

        BotData botData = chatBot.getData();

        if (botData == null){
            return new ResponseEntity<>("BotData not found", HttpStatus.NOT_FOUND);
        }
        int botDataCountNeg = botData.getCountNeg() + 1;

        botData.setCountNeg(botDataCountNeg);
        botDataService.saveBotData(botData);
        return new ResponseEntity<>("ChatBot downvote", HttpStatus.OK);
    }


    @GetMapping("/{chatBotId}/votes")
    // vote sayılarını json olarak geri döndür
    public ResponseEntity<?> getVotes(@PathVariable int chatBotId){
        ChatBot chatBot = chatBotService.findChatBotById(chatBotId);
        if (chatBot == null) {
            return new ResponseEntity<>("ChatBot not found", HttpStatus.NOT_FOUND);
        }
        BotData botData = chatBot.getData();

        if (botData == null){
            return new ResponseEntity<>("BotData not found", HttpStatus.NOT_FOUND);
        }

        VoteDto votes = new VoteDto();
        votes.setCountPos(botData.getCountPos());
        votes.setCountNeg(botData.getCountNeg());

        return ResponseEntity.status(HttpStatus.OK).body(votes);
    }
    //@GetMapping("/{chatBotId}") // bu şimdilik durabilir formdatatı geri döndürecek
}
