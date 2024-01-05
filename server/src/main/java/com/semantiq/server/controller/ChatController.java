package com.semantiq.server.controller;

import com.semantiq.server.DTO.ChatDTO;
import com.semantiq.server.entity.Chat;
import com.semantiq.server.entity.User;
import com.semantiq.server.service.ChatService;
import com.semantiq.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/chats")
@CrossOrigin(origins = "*")
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;
    private final String FILE_PATH = "src/main/resources/Files/Chat/";

    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> loadChat(@PathVariable int id) {
        String filePath = FILE_PATH + String.valueOf(id) + ".json";

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

    @GetMapping("/{id}/data/{email}")
    public ResponseEntity<?> loadChatData(@PathVariable int id, @PathVariable String email) {
        User user = userService.findUserByEmail(email);
        System.out.println(email);
        Chat chat = chatService.findChatById(id);

        // Check if user exists and has access to this chat
        if (user != null && chat != null && user.getBot().getId() == chat.getBot().getId()) {
            ChatDTO chatDTO = new ChatDTO();
            chatDTO.setId(chat.getId());
            chatDTO.setDatetime(chat.getDatetime());
            chatDTO.setVote(chat.getVote());
            chatDTO.setChatbotId(chat.getBot().getId());

            return new ResponseEntity<>(chatDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You don't have access to this chat or invalid email.", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/bots/{botId}")
    public ResponseEntity<List<ChatDTO>> getAllChatsForBot(@PathVariable int botId) {
        List<Chat> botChats = chatService.findAllChatsForBotFromLastYear(botId);
        List<ChatDTO> chatDTOs = new ArrayList<>();

        for (Chat chat : botChats) {
            ChatDTO chatDTO = new ChatDTO();
            chatDTO.setId(chat.getId());
            chatDTO.setDatetime(chat.getDatetime());
            chatDTO.setVote(chat.getVote());
            chatDTO.setChatbotId(chat.getBot().getId());
            chatDTOs.add(chatDTO);
        }

        return new ResponseEntity<>(chatDTOs, HttpStatus.OK);
    }
}
