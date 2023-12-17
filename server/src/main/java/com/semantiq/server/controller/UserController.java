package com.semantiq.server.controller;

import com.semantiq.server.DTO.LoginDTO;
import com.semantiq.server.DTO.SignupDTO;
import com.semantiq.server.entity.User;
import com.semantiq.server.service.UserService;
import org.springframework.http.HttpStatus;
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
        // Check if a user with this email is already registered
        if (userService.findUserByEmail(signupDto.getEmail()) != null) {
            return new ResponseEntity<>("Email is already registered!", HttpStatus.BAD_REQUEST);
        }

        // If a user with this email is not registered, create new one
        User newUser = new User(
                signupDto.getName(),
                signupDto.getSurname(),
                signupDto.getEmail(),
                signupDto.getPassword()
        );

        userService.saveUser(newUser);

        return new ResponseEntity<>("The account has been created successfully.", HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/bots/{botId}")
    public ResponseEntity <?> deleteBotForUser(@PathVariable int userId, @PathVariable int botId) {
        return null;
    }

    @PostMapping("/{userId}/verify")
    public ResponseEntity <?> verify(@PathVariable int userId, @RequestParam int verificationCode) {

        return null;
    }

}
