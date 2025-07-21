package org.example.ecommercewebsite.service;

import org.example.ecommercewebsite.dto.request.AuthRequest;
import org.example.ecommercewebsite.dto.response.AuthResponse;

public interface AuthService {
   AuthResponse login(AuthRequest authRequest);
}
