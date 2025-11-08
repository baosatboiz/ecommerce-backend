package org.example.ecommercewebsite.service;

import org.example.ecommercewebsite.dto.request.Product2Request;
import org.example.ecommercewebsite.dto.request.ProductJsonRequest;
import org.example.ecommercewebsite.dto.request.ProductRequest;
import org.example.ecommercewebsite.dto.response.ProductResponse;
import org.example.ecommercewebsite.entity.Product;
import org.example.ecommercewebsite.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    void createProduct(ProductRequest product, User seller);
    void updateProduct(Long productId, ProductRequest product);
    void deleteProduct(Long productId);
    List<ProductResponse> getProductsBySeller(Long sellerId);
    List<ProductResponse> searchProducts(String search);
    List<ProductResponse> filterProducts(BigDecimal minPrice, BigDecimal maxPrice,Long category);
    void createProductFromJson(List<ProductJsonRequest> productJsonRequest, User seller);
    void createProduct2(List<Product2Request> product2Requests,User seller);
    boolean checkStock(Long variantId);
}
