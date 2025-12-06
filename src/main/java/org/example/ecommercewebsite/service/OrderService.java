package org.example.ecommercewebsite.service;

import org.example.ecommercewebsite.dto.request.OrderRequest;
import org.example.ecommercewebsite.entity.User;

public interface OrderService {
    void createOrder(OrderRequest orderRequest, User user);
}
