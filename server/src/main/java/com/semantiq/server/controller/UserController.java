package com.semantiq.server.controller;

import com.semantiq.server.DTO.LoginDTO;
import com.semantiq.server.DTO.SignupDTO;
import com.semantiq.server.DTO.UserDTO;
import com.semantiq.server.DTO.VerificationDTO;
import com.semantiq.server.entity.User;
import com.semantiq.server.service.EmailService;
import com.semantiq.server.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity <?> authenticateUser(@RequestBody LoginDTO loginDto) {
        // Check if user exists
        if (userService.findUserByEmail(loginDto.getEmail()) == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }

        // Check if user not verified
        if (!userService.findUserByEmail((loginDto.getEmail())).isVerified()) {
            return new ResponseEntity<>("User not verified! Please verify your email address.", HttpStatus.FORBIDDEN);
        }

        // Check password
        if (userService.checkPassword(loginDto.getPassword(), loginDto.getEmail())) {
            return new ResponseEntity<>("Successfully logged in.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Password did not match.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity <?> registerUser(@RequestBody SignupDTO signupDto) throws MessagingException {
        // Check if a user with this email is already registered
        if (userService.findUserByEmail(signupDto.getEmail()) != null) {
            return new ResponseEntity<>("Email is already registered!", HttpStatus.BAD_REQUEST);
        }

        // If a user with this email is not registered, create new one
        User newUser = new User(
                signupDto.getName(),
                signupDto.getSurname(),
                signupDto.getCompany(),
                signupDto.getEmail(),
                userService.encodePassword(signupDto.getPassword())
        );

        userService.saveUser(newUser);

        // Send verification code to email address
        emailService.sendVerificationCode(newUser.getEmail());

        return new ResponseEntity<>("The account has been created successfully.", HttpStatus.CREATED);
    }

    @DeleteMapping("/{email}/bots")
    public ResponseEntity<?> deleteBotForUser(@PathVariable String email) {
        if (userService.deleteBot(email)) {
            return new ResponseEntity<>("Bot removed!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User has no bot!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{email}/verify")
    public ResponseEntity<?> verify(@PathVariable String email, @RequestBody VerificationDTO vDTO) {
        // Check if user is already verified
        if (userService.findUserByEmail(email).isVerified()) {
            return new ResponseEntity<>("The user is already verified!", HttpStatus.BAD_REQUEST);
        }

        if (userService.verifyUser(email, vDTO.getCode())) {
            return new ResponseEntity<>("Email address has been successfully verified.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Verification code did not match!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{email}/load")
    public ResponseEntity<UserDTO> load(@PathVariable String email) {
        User user = userService.findUserByEmail(email);

        if (user == null) {
            // Handle the case where the user is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setCompany(user.getCompany());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());

        // Return UserDTO in the ResponseEntity with HTTP status OK (200)
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
