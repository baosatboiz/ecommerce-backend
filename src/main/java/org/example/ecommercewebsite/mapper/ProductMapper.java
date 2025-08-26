package org.example.ecommercewebsite.mapper;

import org.example.ecommercewebsite.dto.request.ProductJsonRequest;
import org.example.ecommercewebsite.dto.request.ProductRequest;
import org.example.ecommercewebsite.dto.response.ProductImageResponse;
import org.example.ecommercewebsite.dto.response.ProductResponse;
import org.example.ecommercewebsite.entity.Category;
import org.example.ecommercewebsite.entity.Product;
import org.example.ecommercewebsite.entity.ProductImage;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "categoryId", target = "category",qualifiedByName = "namedCategory")
    @BeanMapping(nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source="images",target="images", ignore = true)

    void updateProductFromDto(ProductRequest dto,@MappingTarget Product product);

    @Named("namedCategory")
    static Category mapCategory(Long id){
        if(id == null) return null;
        Category category = new Category();
        category.setId(id);
        return category;
    };
    @Mapping(source = "categoryId", target = "category",qualifiedByName = "namedCategory")
    @Mapping(source="images",target="images", ignore = true)
    Product toEntity(ProductRequest productRequest);

    @Mapping(source = "categoryId", target = "category",qualifiedByName = "namedCategory")
    @Mapping(source = "image_urls", target = "images", qualifiedByName = "namedImageReverse")
    Product toEntity(ProductJsonRequest productJsonRequest);

    @Mapping(source = "images", target="imageUrls",qualifiedByName = "namedImage")
    ProductResponse toDto(Product product);

    @Named("namedImage")
    static List<String> mapImageToDto(List<ProductImage> images) {
        if(images == null) return null;
        return images.stream().map(ProductImage::getUrl).collect(Collectors.toList());
    }

    @Named("namedImageReverse")
    static List<ProductImage> mapImageToEntity(List<String> urls) {
        if (urls == null) return null;
        return urls.stream()
                .map(url -> {
                    ProductImage image = new ProductImage();
                    image.setUrl(url);
                    return image;
                })
                .collect(Collectors.toList());
    }

}
