package org.example.ecommercewebsite.service;

import org.example.ecommercewebsite.dto.request.RegisterRequest;
import org.example.ecommercewebsite.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    void register(RegisterRequest registerRequest);
}
