package com.example.springg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springg.model.UserModel;
import com.example.springg.security.JwtUtil;
import com.example.springg.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        UserModel saved = userService.register(user);
        return ResponseEntity.ok("User registered: " + saved.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {

        UserModel db = userService.findByUsername(user.getUsername());

        if (db == null)
            return ResponseEntity.status(404).body("User not found");

        if (!userService.matchesRawPassword(user.getPassword(), db.getPassword()))
            return ResponseEntity.status(401).body("Invalid credentials");

        String token = jwtUtil.generateToken(db.getUsername());
        return ResponseEntity.ok(token);
    }
}
