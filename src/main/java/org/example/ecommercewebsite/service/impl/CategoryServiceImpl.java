package org.example.ecommercewebsite.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.dto.request.CategoryRequest;
import org.example.ecommercewebsite.dto.response.CategoryResponse;
import org.example.ecommercewebsite.entity.Category;
import org.example.ecommercewebsite.mapper.CategoryMapper;
import org.example.ecommercewebsite.repository.CategoryRepository;
import org.example.ecommercewebsite.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toCategory(categoryRequest);
       categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long id, CategoryRequest category) {
      Category existing = categoryRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Category not found"));
      categoryMapper.updateCategory(category,existing);
      categoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)) throw new RuntimeException("Category not found");
        categoryRepository.deleteById(id);
    }
}
