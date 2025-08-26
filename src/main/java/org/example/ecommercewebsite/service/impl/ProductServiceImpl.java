package org.example.ecommercewebsite.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.dto.request.ProductJsonRequest;
import org.example.ecommercewebsite.dto.request.ProductRequest;
import org.example.ecommercewebsite.dto.response.ProductResponse;
import org.example.ecommercewebsite.entity.Product;
import org.example.ecommercewebsite.entity.ProductImage;
import org.example.ecommercewebsite.entity.User;
import org.example.ecommercewebsite.mapper.ProductMapper;
import org.example.ecommercewebsite.repository.ProductRepository;
import org.example.ecommercewebsite.service.ProductService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productMapper.toDto(productRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    public void createProduct(ProductRequest productRequest, User seller) {
        Product product = productMapper.toEntity(productRequest);
        product.setSeller(seller);

        List<MultipartFile> images = productRequest.getImages();
        List<ProductImage> imageEntities = new ArrayList<>();

        if (images != null && !images.isEmpty()) {
            Path uploadPath = Paths.get("uploads");
            try {
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                for (MultipartFile image : images) {
                    String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    ProductImage productImage = new ProductImage();
                    productImage.setUrl("/uploads/"+fileName);
                    productImage.setProduct(product);
                    imageEntities.add(productImage);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload images", e);
            }
        }

        product.setImages(imageEntities);
        productRepository.save(product); // sẽ cascade lưu luôn images
    }

    @Override
    @PreAuthorize("@securityService.isProductOwner(#productId,authentication.principal.id,authentication.principal.role)")
    public void updateProduct(Long productId , ProductRequest product) {
          Product existingProduct = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
          productMapper.updateProductFromDto(product,existingProduct);
          productRepository.save(existingProduct);
    }

    @Override
    @PreAuthorize("@securityService.isProductOwner(#productId,authentication.principal.id,authentication.principal.role)")
    public void deleteProduct(Long productId) {
        if(!productRepository.existsById(productId)) { throw new RuntimeException("Product not found"); }
        productRepository.deleteById(productId);
    }

    @Override
    public List<ProductResponse> getProductsBySeller(Long sellerId) {
        return productRepository.findAllBySeller_id(sellerId).stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> searchProducts(String search) {
        return productRepository.findByNameContainingIgnoreCase(search).stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Query(value = "select p from Product p where :minPrice <=p.price and p.price >=:maxPrice and p.category.id=:category")
    public List<ProductResponse> filterProducts(BigDecimal minPrice, BigDecimal maxPrice, Long category){
        return productRepository.filterProduct(minPrice,maxPrice,category).stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void createProductFromJson(List<ProductJsonRequest> productJsonRequest, User seller) {
        List<Product> products = productJsonRequest.stream().map(productMapper::toEntity)
                .peek(product -> {
                    product.setSeller(seller);
                    if(product.getImages()!=null){
                        for(ProductImage image: product.getImages()){
                            image.setProduct(product);
                        }
                    }
                }).toList();
        productRepository.saveAll(products);
    }
}
