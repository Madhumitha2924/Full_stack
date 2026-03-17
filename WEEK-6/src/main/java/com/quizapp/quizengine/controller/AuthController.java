package com.quizapp.quizengine.controller;

import com.quizapp.quizengine.model.User;
import com.quizapp.quizengine.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Task 6.5 Part 1 - Register with validation
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully!");
        response.put("user", user.getName());
        response.put("email", user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ Task 6.5 Part 3 - Login with JWT
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Dummy validation - in real app you'd check database
        if ("admin".equals(username) && "password123".equals(password)) {
            String token = jwtUtil.generateToken(username);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful!");
            response.put("token", token);
            response.put("username", username);
            return ResponseEntity.ok(response);
        }

        Map<String, Object> error = new HashMap<>();
        error.put("message", "Invalid username or password!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}