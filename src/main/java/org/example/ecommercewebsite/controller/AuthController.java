package org.example.ecommercewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.dto.request.AuthRequest;
import org.example.ecommercewebsite.dto.request.RegisterRequest;
import org.example.ecommercewebsite.dto.response.AuthResponse;
import org.example.ecommercewebsite.service.AuthService;
import org.example.ecommercewebsite.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            return ResponseEntity.ok(authService.login(authRequest));
        }
        catch(BadCredentialsException e){
            return ResponseEntity.status(404).body("Invalid credentials");

        }
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest) {
            userService.register(registerRequest);
            return ResponseEntity.ok().build();

    }
}
