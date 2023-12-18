package com.semantiq.server.controller;

import com.semantiq.server.entity.ChatBot;
import com.semantiq.server.entity.User;
import com.semantiq.server.service.ChatBotService;
import com.semantiq.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/bots")

public class ChatBotController {
    private final ChatBotService chatbotService;
    private final UserService userService;


    @Autowired
    public ChatBotController(ChatBotService chatbotService, UserService userService) {
        this.chatbotService = chatbotService;
        this.userService = userService;
    }


    @PostMapping("{/{botName}/users/{id}")
    public ResponseEntity<?> createBot(@PathVariable int id, @PathVariable String botName, @RequestBody String formData) {
        // Check if user already has a chatbot
        if (userService.findUserById(id).getBot() != null) {
            return new ResponseEntity<>("User already has a chat bot.", HttpStatus.FORBIDDEN);
        }

        // Add limit check

        // Then create bot
        User user = userService.findUserById(id);
        ChatBot chatBot = new ChatBot();
        chatBot.setBotName(botName);



        return new ResponseEntity<>("Chatbot Created", HttpStatus.OK);
    }


    @PostMapping("{botId}/generate-pdf")
    public ResponseEntity <?> createPDF(@PathVariable int botId, @RequestBody String pdfData) {

        return null;
    }
}
