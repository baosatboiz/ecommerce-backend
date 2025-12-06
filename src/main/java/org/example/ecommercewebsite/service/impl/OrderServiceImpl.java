package org.example.ecommercewebsite.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.dto.request.OrderItemRequest;
import org.example.ecommercewebsite.dto.request.OrderRequest;
import org.example.ecommercewebsite.entity.*;
import org.example.ecommercewebsite.mapper.OrderMapper;
import org.example.ecommercewebsite.repository.OrderRepository;
import org.example.ecommercewebsite.repository.ProductRepository;
import org.example.ecommercewebsite.repository.ProductVariantRepository;
import org.example.ecommercewebsite.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public void createOrder(OrderRequest orderRequest, User user) {
        orderRequest.getOrderItems().forEach(this::decreaseStockAtomic);
        Order order = orderMapper.toEntity(orderRequest);
        BigDecimal total = BigDecimal.ZERO;
        for(int i=0;i<orderRequest.getOrderItems().size();i++){
            OrderItemRequest req = orderRequest.getOrderItems().get(i);
            OrderItem item = order.getOrderItems().get(i);
            if(req.getProductId()!=null){
                Product product = productRepository.findById(req.getProductId())
                        .orElseThrow(RuntimeException::new);
                item.setPrice(product.getPrice());
                item.setProduct(product);
            }
            else {
                ProductVariant productVariant =productVariantRepository.findById(req.getVariantId())
                        .orElseThrow(RuntimeException::new);
                item.setPrice(productVariant.getPrice());
                item.setVariant(productVariant);
            }
            item.setOrder(order);
        }
        order.setTotalPrice(order.getOrderItems().stream()
                        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setUser(user);
        orderRepository.save(order);

    }

    public void decreaseStockAtomic(OrderItemRequest orderItem){
        int update = 0;
        if(orderItem.getProductId()!=null) update = productRepository.decreseProductStock(orderItem.getProductId(),orderItem.getQuantity());
         else update = productVariantRepository.decreseProductStock(orderItem.getVariantId(),orderItem.getQuantity());
        if(update==0) throw new RuntimeException("This product is out of stock");
        }
}
