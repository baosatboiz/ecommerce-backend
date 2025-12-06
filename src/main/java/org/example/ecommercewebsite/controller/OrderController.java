package org.example.ecommercewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.dto.request.OrderRequest;
import org.example.ecommercewebsite.entity.User;
import org.example.ecommercewebsite.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    ResponseEntity<Boolean> createOrder(@RequestBody OrderRequest orderRequest, @AuthenticationPrincipal User user){
       orderService.createOrder(orderRequest,user);
       return ResponseEntity.ok(true);
    }
}
