package com.echowire.userservice.controller;

import com.echowire.userservice.dto.LoginRequest;
import com.echowire.userservice.dto.RegisterRequest;
import com.echowire.userservice.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(HttpServletRequest httpRequest, @RequestBody RegisterRequest request) {
        authService.insertUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest httpRequest, @RequestBody LoginRequest request) {
        var token = authService.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login2")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> test(HttpServletRequest httpRequest, @RequestBody LoginRequest request) {
//        var token = authService.login(request);
        return ResponseEntity.ok(Map.of("token", 123));
    }
}
