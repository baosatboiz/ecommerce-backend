package org.example.ecommercewebsite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private String name;
    private String description;
    private String imageUrl;
}
