package com.semantiq.server.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/botdata")
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

    @PostMapping("/voteBot/{chatBotId}/{vote}")
    public ResponseEntity<?> voteBot(@PathVariable int chatBotId, @PathVariable String vote, @PathVariable int chatId){
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
        LocalDate today = LocalDate.now();
        LocalDate fifteenDaysAgo = today.minusDays(14); // 15 days ago

        Map<LocalDate, Long> chatCountsByDate = chatService.getChatCountByDateRange(chatBotId, fifteenDaysAgo, today);

        List<ObjectNode> chatCountData = new ArrayList<>();
        for (LocalDate date = fifteenDaysAgo; !date.isAfter(today); date = date.plusDays(1)) {
            long chatCount = chatCountsByDate.getOrDefault(date, 0L);

            ObjectNode data = JsonNodeFactory.instance.objectNode();
            data.put("date", date.toString());
            data.put("totalUsage", chatCount);

            chatCountData.add(data);
        }

        return ResponseEntity.ok(chatCountData);
    }

    //@GetMapping("/{chatBotId}") // bu şimdilik durabilir formdatatı geri döndürecek
}
