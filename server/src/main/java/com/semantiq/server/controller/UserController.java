package com.semantiq.server.controller;

import com.semantiq.server.DTO.LoginDTO;
import com.semantiq.server.DTO.SignupDTO;
import com.semantiq.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity <?> authenticateUser(@RequestBody LoginDTO loginDto) {

        return null;
    }

    @PostMapping("/signup")
    public ResponseEntity <?> registerUser(@RequestBody SignupDTO signupDto) {

        return null;
    }

    @DeleteMapping("/{userId}/bots/{botId}")
    public ResponseEntity <?> deleteBotForUser(@PathVariable int userId, @PathVariable int botId) {
		/*
		// Kullanıcıya ait botu silme işlemi UserService'ten çağrılır
		boolean deleted = userService.deleteBot(userId, botId);

		if (deleted) {
			return ResponseEntity.ok("Bot successfully deleted!");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bot or user not found!");
		}
		*/

        return null;
    }

    @PostMapping("/{userId}/reset-password")
    public ResponseEntity <?> resetPassword(@PathVariable int userId, @RequestParam int verificationCode, @RequestParam String newPassword) {
		/*
		boolean isPasswordReset = userService.resetPassword(userId, verificationCode, newPassword);
		if (isPasswordReset) {
			return ResponseEntity.ok("Password reset successful!");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password reset failed!");
		}
		*/

        return null;
    }

}
