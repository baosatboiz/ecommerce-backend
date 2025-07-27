package org.example.ecommercewebsite.mapper;

import org.example.ecommercewebsite.dto.request.ProductRequest;
import org.example.ecommercewebsite.entity.Category;
import org.example.ecommercewebsite.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "categoryId", target = "category",qualifiedByName = "namedCategory")
    @BeanMapping(nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(ProductRequest dto,@MappingTarget Product product);

    @Named("namedCategory")
    static Category mapCategory(Long id){
        if(id == null) return null;
        Category category = new Category();
        category.setId(id);
        return category;
    };
    @Mapping(source = "categoryId", target = "category",qualifiedByName = "namedCategory")
    Product toEntity(ProductRequest productRequest);

}
