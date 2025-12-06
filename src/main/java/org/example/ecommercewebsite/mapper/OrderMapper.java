package org.example.ecommercewebsite.mapper;

import org.example.ecommercewebsite.dto.request.OrderRequest;
import org.example.ecommercewebsite.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderRequest orderRequest);
}
