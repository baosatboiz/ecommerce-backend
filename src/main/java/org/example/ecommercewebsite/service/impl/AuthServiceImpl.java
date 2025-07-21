package org.example.ecommercewebsite.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.dto.request.AuthRequest;
import org.example.ecommercewebsite.dto.response.AuthResponse;
import org.example.ecommercewebsite.entity.User;
import org.example.ecommercewebsite.security.JwtService;
import org.example.ecommercewebsite.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthResponse login(AuthRequest authRequest) {
        try{
           Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
           User user = (User) auth.getPrincipal();
           String token =jwtService.generateToken(authRequest.getUsername(),user.getRole().name());
            return AuthResponse.builder()
                    .token(token)
                    .username(authRequest.getUsername())
                    .role(user.getRole().name())
                    .build();
        }
        catch(BadCredentialsException e){
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
