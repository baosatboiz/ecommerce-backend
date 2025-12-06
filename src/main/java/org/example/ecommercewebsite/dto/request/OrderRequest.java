package org.example.ecommercewebsite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String address;
    private String phoneNumber;
    private List<OrderItemRequest> orderItems;
}
