package com.semantiq.server.controller;

import com.semantiq.server.DTO.VoteDto;
import com.semantiq.server.entity.BotData;
import com.semantiq.server.entity.ChatBot;
import com.semantiq.server.service.BotDataService;
import com.semantiq.server.service.ChatBotService.ChatBotService;
import com.semantiq.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/botdata")
@CrossOrigin(origins = "*")
public class BotDataController {
    private final BotDataService botDataService;
    private final ChatBotService chatBotService;
    private final ChatService chatService;

    @Autowired
    public BotDataController(BotDataService botDataService, ChatBotService chatBotService, ChatService chatService) {
        this.botDataService = botDataService;
        this.chatBotService = chatBotService;
        this.chatService = chatService;
    }

    @PostMapping("/{chatBotId}/chats/{chatId}/{vote}")
    public ResponseEntity<?> voteBot(@PathVariable int chatBotId, @PathVariable int vote, @PathVariable int chatId){
        ChatBot chatBot = chatBotService.findChatBotById(chatBotId);
        if (chatBot == null) {
            return new ResponseEntity<>("ChatBot not found", HttpStatus.NOT_FOUND);
        }

        BotData botData = chatBot.getData();

        if (botData == null){
            return new ResponseEntity<>("BotData not found", HttpStatus.NOT_FOUND);
        }
        try{
            botDataService.voteBot(botData, vote);
        }catch(InvalidParameterException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        chatService.setVote(chatId, vote);

        return new ResponseEntity<>("ChatBot voted", HttpStatus.OK);
    }

    @GetMapping("/{chatBotId}/votes")
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

    @GetMapping("/bots/{chatBotId}/chats-count")
    public ResponseEntity<?> getChatsCountLast15Days(@PathVariable int chatBotId) {
        List<Map<String, Object>> chatCounts = chatService.getChatsCountLast15Days(chatBotId);
        return ResponseEntity.status(HttpStatus.OK).body(chatCounts);
    }
}