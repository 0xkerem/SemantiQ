package com.semantiq.server.controller;

import com.semantiq.server.service.ChatBotService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/bots")

public class ChatBotController {
    private final ChatBotService chatbotService;


    @Autowired
    public ChatBotController(ChatBotService chatbotService) {
        this.chatbotService = chatbotService;
    }


    @PostMapping
    public ResponseEntity<?> createBot(@RequestBody String botData) {
		/*
		boolean isBotCreated = chatbotService.createBot(botData);

		if (isBotCreated) {
			return ResponseEntity.ok("Bot created successfully!");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bot creation failed!");
		}
		*/

        return null;
    }


    @PostMapping("{botId}/generate-pdf")
    public ResponseEntity <?> createPDF(@PathVariable int botId, @RequestBody String pdfData) {

        return null;
    }
}
