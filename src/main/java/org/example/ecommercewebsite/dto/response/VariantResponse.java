package org.example.ecommercewebsite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantResponse {
    private Long id;
    private BigDecimal price;
    private int stock;
    private String imgUrl;
    private Map<String,String> attributes;
}
