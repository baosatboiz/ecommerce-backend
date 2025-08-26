package org.example.ecommercewebsite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductJsonRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private List<String> image_urls;
}
