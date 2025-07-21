package org.example.ecommercewebsite.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class test {
    private final JwtService jwtService;
    @GetMapping("/hello")
    public Claims hello(){
        return jwtService.parseToken(jwtService.generateToken("giabao","admin"));

    }
}
