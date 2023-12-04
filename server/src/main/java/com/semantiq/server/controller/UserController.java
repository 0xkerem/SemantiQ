package com.semantiq.server.controller;

import com.semantiq.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
public class UserController {
    private final UserService UserService;

    @Autowired
    public  UserController(UserService UserService) {
        this.UserService = UserService;
    }

    @PostMapping("/{email}")
    public ResponseEntity<?> login(@PathVariable String email, @RequestBody String password) {

    }
}
