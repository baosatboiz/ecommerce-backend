package org.example.ecommercewebsite.mapper;

import org.example.ecommercewebsite.dto.request.Product2Request;
import org.example.ecommercewebsite.dto.request.ProductJsonRequest;
import org.example.ecommercewebsite.dto.request.ProductRequest;
import org.example.ecommercewebsite.dto.response.ProductResponse;
import org.example.ecommercewebsite.dto.response.VariantResponse;
import org.example.ecommercewebsite.entity.*;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = OptionMapper.class)
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
    @Mapping(source="variants",target = "variants",qualifiedByName = "namedVariant")
    @Mapping(source = "seller",target = "sellerName",qualifiedByName = "namedSeller")
    ProductResponse toDto(Product product);

    @Mapping(source = "images",target="images",qualifiedByName = "namedImageReverse")
    @Mapping(source = "variants",target="variants",ignore = true)
    Product toEntity(Product2Request product2Request);

    @Named("namedImage")
    static List<String> mapImageToDto(List<ProductImage> images) {
        if(images == null) return null;
        return images.stream().map(ProductImage::getUrl).collect(Collectors.toList());
    }

    @Named("namedSeller")
    static String mapSeller(User seller){
        return seller.getUsername();
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
    @Named("namedVariant")
    static List<VariantResponse> mapVariants(List<ProductVariant> variants) {
        if (variants == null) return null;
        return variants.stream().map(variant -> {
            VariantResponse vr = new VariantResponse();
            vr.setId(variant.getId());
            vr.setPrice(variant.getPrice());
            vr.setStock(variant.getStock());
            vr.setImgUrl(variant.getImageUrl() != null ? variant.getImageUrl() : "");

            // map attributes safely
            if (variant.getOptionValueSet() != null) {
                vr.setAttributes(
                        variant.getOptionValueSet().stream()
                                .filter(v -> v.getOption() != null)
                                .collect(Collectors.toMap(
                                        v -> v.getOption().getName(),
                                        OptionValue::getValue
                                ))
                );
            }
            return vr;
        }).collect(Collectors.toList());
    }
    @AfterMapping
    default void mapVariant(@MappingTarget Product product,Product2Request product2Request){
        for(Option option:product.getOptions()) option.setProduct(product);
        product.setVariants(
                product2Request.getVariants().stream()
                        .map(variantRequest -> {
                            ProductVariant productVariant = new ProductVariant();
                            productVariant.setImageUrl(variantRequest.getImages().getFirst());
                            productVariant.setStock(variantRequest.getQty());
                            productVariant.setPrice(variantRequest.getPrice());
                            Set<OptionValue> ov = new HashSet<>();
                            if(variantRequest.getAttributes()!=null)
                             variantRequest.getAttributes().forEach((optionNameReq,valueReq)->{
                                product.getOptions().stream().filter(o->o.getName().equalsIgnoreCase(optionNameReq))
                                        .flatMap(o->o.getOptionValueSet().stream())
                                        .filter(value->value.getValue().equalsIgnoreCase(valueReq))
                                        .findFirst().ifPresent(ov::add);
                             });
                            productVariant.setProduct(product);
                            productVariant.setOptionValueSet(ov);
                            return productVariant;
                        }).collect(Collectors.toList()));

    }
}

