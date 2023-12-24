package com.semantiq.server.service;

import com.semantiq.server.repository.UserRepo;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserRepo userRepo;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, UserRepo userRepo) {
        this.javaMailSender = javaMailSender;
        this.userRepo = userRepo;
    }

    // Method to send a verification code via email
    public void sendVerificationCode(String recipientEmail) throws MessagingException {
        int verificationCode = userRepo.findByEmail(recipientEmail).getVerificationCode();
        String subject = "Verification Code";
        String message = "Your verification code is: " + String.valueOf(verificationCode) +
                "\nEnter the code in the box on the sign-up screen or try logging in with this email to re-enter the code.";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom("semantiq.app@gmail.com");
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(message, true);

        javaMailSender.send(mimeMessage);
    }

    // New method for sending a report to the owner when the user is dissatisfied
    public void sendReport(String userEmail, String ownerEmail, int chatId) throws MessagingException {
        String subject = "User Dissatisfaction Report";
        String message = "User with email " + userEmail + " is dissatisfied with the chatbot's answer.\n"
                + "Chat ID: " + chatId + "\n\nPlease review the conversation history on your panel.";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom("semantiq.app@gmail.com");
        helper.setTo(ownerEmail);
        helper.setSubject(subject);
        helper.setText(message, true);

        javaMailSender.send(mimeMessage);
    }
}
