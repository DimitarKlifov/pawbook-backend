package com.dimitar.booking.controllers;

import com.dimitar.booking.dto.RegisterRequest;
import com.dimitar.booking.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request){
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Registration successful. Pleas check your email to verify your account.");
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verify(@RequestParam String token){
        String jwt = authService.verifyEmail(token);
        return ResponseEntity.ok(Map.of("token", jwt));
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password){
        String jwt = authService.login(email,password);
        return ResponseEntity.ok(Map.of("token" , jwt));
    }
}
