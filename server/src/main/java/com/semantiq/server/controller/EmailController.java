package com.semantiq.server.controller;

import com.semantiq.server.DTO.ReportEmailDTO;
import com.semantiq.server.service.ChatBotService.ChatBotService;
import com.semantiq.server.service.EmailService;
import com.semantiq.server.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailController {
    private final EmailService emailService;
    private final UserService userService;
    private final ChatBotService chatBotService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService, ChatBotService chatBotService) {
        this.emailService = emailService;
        this.userService = userService;
        this.chatBotService = chatBotService;
    }

    @PostMapping("/report")
    public ResponseEntity<?> sendReportEmail(@RequestBody ReportEmailDTO reportEmailDTO) {
        try {
            if (chatBotService.findChatBotById(reportEmailDTO.getChatBotId()) == null) {
                return new ResponseEntity<>("Bot not found", HttpStatus.NOT_FOUND);
            }

            String ownerEmail = chatBotService.findChatBotById(reportEmailDTO.getChatBotId()).getOwner().getEmail();
            emailService.sendReport(reportEmailDTO.getUserEmail(), ownerEmail, reportEmailDTO.getChatId());
            return new ResponseEntity<>("Mail sent.", HttpStatus.OK);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Failed to send email.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}