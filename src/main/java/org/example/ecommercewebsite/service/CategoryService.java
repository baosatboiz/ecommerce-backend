package org.example.ecommercewebsite.service;

import org.example.ecommercewebsite.dto.request.CategoryRequest;
import org.example.ecommercewebsite.dto.response.CategoryResponse;
import org.example.ecommercewebsite.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    void createCategory(CategoryRequest categoryRequest);

    void updateCategory(Long id, CategoryRequest category);

    void deleteCategory(Long id);
}
