package com.example.gestionproduit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionproduit.entity.Users;
import com.example.gestionproduit.service.UserService;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;
    
    public AccountController(UserService userService) {
    	this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> createAccount(@RequestBody Users user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse("Account created successfully"));
    }
    
    public record AuthResponse(String response) {}
}