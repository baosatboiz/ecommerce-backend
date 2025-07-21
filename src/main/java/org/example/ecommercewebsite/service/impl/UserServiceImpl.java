package org.example.ecommercewebsite.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.dto.request.RegisterRequest;
import org.example.ecommercewebsite.entity.Role;
import org.example.ecommercewebsite.entity.User;
import org.example.ecommercewebsite.repository.UserRepository;
import org.example.ecommercewebsite.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        if (registerRequest.getUsername() == null) {
            throw new RuntimeException("Username is required");
        }
        if (registerRequest.getPassword() == null) {
            throw new RuntimeException("Password is required");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        if (userRepository.existsByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail())) {
            throw new RuntimeException("Username or email already exists");
        }
         userRepository.save(User.builder()
                 .username(registerRequest.getUsername())
                 .password(passwordEncoder.encode(registerRequest.getPassword()))
                 .email(registerRequest.getEmail())
                         .role(Role.USER)
                 .build());
    }
}
