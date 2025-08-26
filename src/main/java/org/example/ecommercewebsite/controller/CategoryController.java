package org.example.ecommercewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.dto.request.CategoryRequest;
import org.example.ecommercewebsite.dto.response.CategoryResponse;
import org.example.ecommercewebsite.entity.Category;
import org.example.ecommercewebsite.mapper.CategoryMapper;
import org.example.ecommercewebsite.repository.CategoryRepository;
import org.example.ecommercewebsite.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/category")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

}
