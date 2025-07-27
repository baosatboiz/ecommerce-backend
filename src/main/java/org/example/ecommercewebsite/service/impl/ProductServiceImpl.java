package org.example.ecommercewebsite.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.dto.request.ProductRequest;
import org.example.ecommercewebsite.entity.Product;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void createProduct(ProductRequest productRequest, User seller) {
        Product product = productMapper.toEntity(productRequest);
        product.setSeller(seller);
        MultipartFile image = productRequest.getImage();
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get("uploads");
            try {
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Files.copy(image.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                product.setImageUrl("/uploads/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }
         productRepository.save(product);
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
    public List<Product> getProductsBySeller(Long sellerId) {
        return productRepository.findAllBySeller_id(sellerId);
    }

    @Override
    public List<Product> searchProducts(String search) {
        return productRepository.findByName(search);
    }

    @Override
    @Query(value = "select p from Product p where :minPrice <=p.price and p.price >=:maxPrice and p.category.id=:category")
    public List<Product> filterProducts(BigDecimal minPrice, BigDecimal maxPrice, Long category){
        return productRepository.filterProduct(minPrice,maxPrice,category);
    }
}
