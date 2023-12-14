package com.semantiq.server.controller;

import com.semantiq.server.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/chat")

public class ChatController {
    private final ChatService chatService;


    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @GetMapping("/load")
    public ResponseEntity <?> loadChat() {

        return null;
    }
}
