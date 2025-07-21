package org.example.ecommercewebsite.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.entity.User;
import org.example.ecommercewebsite.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
         final String authHeader = request.getHeader("Authorization");
         final String jwt;
         final String username;
         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
             filterChain.doFilter(request, response);
             return;
         }
         jwt = authHeader.substring(7);
         try {
             username = jwtService.parseToken(jwt).getSubject();
             if(SecurityContextHolder.getContext().getAuthentication() == null) {
                 User user = userService.findByUsername(username)
                         .orElseThrow(() -> new UsernameNotFoundException(username));
                 UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
                 SecurityContextHolder.getContext().setAuthentication(auth);
             }
         }
         catch (Exception e) {
         }
         filterChain.doFilter(request, response);

    }
}
