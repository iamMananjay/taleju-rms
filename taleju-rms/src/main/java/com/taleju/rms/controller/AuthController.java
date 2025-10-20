package com.taleju.rms.controller;

import com.taleju.rms.dto.LoginRequest;
import com.taleju.rms.dto.RegisterRequest;
import com.taleju.rms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        String token = authService.register(request);
        return ResponseEntity.ok("User registered successfully. Token: " + token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok("Login successful. Token: " + token);
    }
}
