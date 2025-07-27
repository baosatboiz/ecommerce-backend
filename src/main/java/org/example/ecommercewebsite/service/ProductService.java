package org.example.ecommercewebsite.service;

import org.example.ecommercewebsite.dto.request.ProductRequest;
import org.example.ecommercewebsite.entity.Product;
import org.example.ecommercewebsite.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    void createProduct(ProductRequest product, User seller);
    void updateProduct(Long productId, ProductRequest product);
    void deleteProduct(Long productId);
    List<Product> getProductsBySeller(Long sellerId);
    List<Product> searchProducts(String search);
    List<Product> filterProducts(BigDecimal minPrice, BigDecimal maxPrice,Long category);
}
