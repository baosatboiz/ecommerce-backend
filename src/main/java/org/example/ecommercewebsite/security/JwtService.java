package org.example.ecommercewebsite.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    public Key getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(String username, String role){
         return Jwts.builder()
                 .setSubject(username)
                 .claim("role",role)
                 .setIssuedAt(Date.from(Instant.now()))
                 .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
                 .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                 .compact();
    }
    public Claims parseToken(String token){
           return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}
