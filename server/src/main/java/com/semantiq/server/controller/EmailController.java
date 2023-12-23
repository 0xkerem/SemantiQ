package com.semantiq.server.controller;

import com.semantiq.server.service.EmailService;
import com.semantiq.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/email")
public class EmailController {
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/send")
    public ResponseEntity <?> sendEmail(@RequestParam int userId, @RequestParam String message) {

        return null;
    }
}