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
        String message = "Your verification code is: " + String.valueOf(verificationCode);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom("your_email@example.com");
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(message, true);

        javaMailSender.send(mimeMessage);
    }
}
