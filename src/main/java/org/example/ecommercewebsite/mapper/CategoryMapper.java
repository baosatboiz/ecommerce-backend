package org.example.ecommercewebsite.mapper;

import org.example.ecommercewebsite.dto.request.CategoryRequest;
import org.example.ecommercewebsite.dto.response.CategoryResponse;
import org.example.ecommercewebsite.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategory(CategoryRequest categoryRequest, @MappingTarget  Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "image",target = "imageUrl")
    @Mapping(source="display_name",target="name")
    Category toCategory(CategoryRequest categoryRequest);

    CategoryResponse toCategoryResponse(Category category);
}
