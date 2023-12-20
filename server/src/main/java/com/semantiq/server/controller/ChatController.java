package com.semantiq.server.controller;

import com.semantiq.server.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/api/chats")
public class ChatController {
    private final ChatService chatService;
    private final String FILE_PATH = "src/main/resources/Files/Chat/";

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> loadChat(@PathVariable String id) {
        String filePath = FILE_PATH + id + ".json";

        // Check if the file exists
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            try {
                // Read file contents
                String content = new String(Files.readAllBytes(path));
                return ResponseEntity.ok().body(content);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading the file");
            }
        } else {
            return ResponseEntity.notFound().build(); // File not found
        }
    }
}
