package org.example.ecommercewebsite.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VariantRequest {
    private BigDecimal price;
    private int qty;
    private Map<String,String> attributes;
    private List<String> images;
}
