package com.semantiq.server.controller;

import com.semantiq.server.entity.BotData;
import com.semantiq.server.entity.ChatBot;
import com.semantiq.server.entity.User;
import com.semantiq.server.service.BotDataService;
import com.semantiq.server.service.ChatBotService.ChatBotService;
import com.semantiq.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bots")
@CrossOrigin(origins = "*")
public class ChatBotController {
    private final ChatBotService chatbotService;
    private final UserService userService;
    private final BotDataService botDataService;

    @Autowired
    public ChatBotController(ChatBotService chatbotService, UserService userService, BotDataService botDataService) {
        this.chatbotService = chatbotService;
        this.userService = userService;
        this.botDataService = botDataService;
    }

    @PostMapping("/{botName}/users/{id}")
    public ResponseEntity<?> createBot(@PathVariable int id, @PathVariable String botName, @RequestBody String formData) {
        User user = userService.findUserById(id);

        // Check if user exists
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Check if user already has a chatbot
        if (user.getBot() != null) {
            return new ResponseEntity<>("User already has a chat bot.", HttpStatus.FORBIDDEN);
        }

        // Create bot
        ChatBot chatBot = new ChatBot();
        chatBot.setBotName(botName);
        chatBot = chatbotService.setBotData(chatBot, formData, id);
        chatBot.setOwner(user);
        chatbotService.saveChatBot(chatBot);

        // Set bot data
        BotData botData = chatBot.getData();
        botData.setBot(chatBot);
        botDataService.saveBotData(botData);

        // Set bot for the user and save the user
        user.setBot(chatBot);
        userService.saveUser(user);

        return new ResponseEntity<>("Chatbot Created", HttpStatus.OK);
    }

    @PostMapping("/users/{id}/update")
    public ResponseEntity<?> updateBot(@PathVariable int id, @RequestBody String formData) {
        User user = userService.findUserById(id);

        // Check if user exists
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        ChatBot chatBot = user.getBot();

        // Check if the user exceeded the limit
        LocalDate lastUpdated = chatBot.getData().getLastUpdated();
        if (lastUpdated != null && lastUpdated.equals(LocalDate.now())) {
            return new ResponseEntity<>("The user reached the daily update limit!", HttpStatus.BAD_REQUEST);
        }

        // Update bot
        chatBot = chatbotService.setBotData(chatBot, formData, id);
        chatBot.getData().setLastUpdated(LocalDate.now()); // Set today's date as last updated

        chatbotService.saveChatBot(chatBot);

        // Set bot for the user and save the user
        user.setBot(chatBot);
        userService.saveUser(user);

        return new ResponseEntity<>("Chatbot Updated", HttpStatus.OK);
    }


    // If chatId is -1 it means create a new chat
    @PostMapping("/{chatBotId}/chat/{chatId}")
    public ResponseEntity<?> ask(@PathVariable int chatBotId, @PathVariable int chatId, @RequestBody String question) {
        if (chatbotService.findChatBotById(chatBotId) == null) {
            return new ResponseEntity<>("There is no bot with id " + chatBotId, HttpStatus.NOT_FOUND);
        }

        Map<String, Object> result = chatbotService.askQuestion(chatBotId, chatId, question);

        if (result.containsKey("responseBody") && result.containsKey("chatId")) {
            String responseBody = (String) result.get("responseBody");
            int newChatId = (int) result.get("chatId");

            // Parse the responseBody as a JSON object
            Map<String, Object> parsedResponseBody = chatbotService.parseJsonResponse(responseBody);

            Map<String, Object> response = new HashMap<>();
            response.put("answer", parsedResponseBody);
            response.put("chatId", newChatId);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Problem occurred!", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/{botId}")
    public ResponseEntity<?> getName(@PathVariable int botId) {
        if (chatbotService.findChatBotById(botId) == null) {
            return new ResponseEntity<>("There is no bot with provided ID.", HttpStatus.NOT_FOUND);
        }
        String botName = chatbotService.findChatBotById(botId).getBotName();
        return new ResponseEntity<>(botName, HttpStatus.OK);
    }
}
