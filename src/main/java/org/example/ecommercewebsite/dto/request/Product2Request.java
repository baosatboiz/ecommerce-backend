package org.example.ecommercewebsite.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Product2Request {
    private String name;
    private BigDecimal price;
    private String type;
    private String description;
    private List<String> images;
    private List<OptionRequest> options;
    private List<VariantRequest> variants;
}
