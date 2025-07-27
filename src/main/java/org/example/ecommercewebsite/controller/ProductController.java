package org.example.ecommercewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.dto.request.ProductRequest;
import org.example.ecommercewebsite.entity.Product;
import org.example.ecommercewebsite.entity.User;
import org.example.ecommercewebsite.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
   private final ProductService productService;
   @GetMapping("/product")
    public List<Product> findAll(@RequestParam(required = false) Long sellerId ,
                                 @RequestParam(required = false) String keyWord,
                                 @RequestParam(required = false)BigDecimal minPrice,
                                 @RequestParam(required = false) BigDecimal maxPrice,
                                 @RequestParam(required = false) Long categoryId) {
       if(sellerId != null){
           return productService.getProductsBySeller(sellerId);
       }
       if(keyWord != null){
           return productService.searchProducts(keyWord);
       }
       if(minPrice != null||maxPrice != null||categoryId != null){
           return productService.filterProducts(minPrice, maxPrice, categoryId);
       }
       return productService.getAllProducts();
   }

   @PostMapping("/product")
   public ResponseEntity<?> createProduct(@ModelAttribute  ProductRequest productRequest, @AuthenticationPrincipal User user) {
       productService.createProduct(productRequest,user);
       return ResponseEntity.ok().build();
   }
   @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequest product) {
       productService.updateProduct(id, product);
       return ResponseEntity.ok().build();
   }
   @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
       productService.deleteProduct(id);
       return ResponseEntity.ok().build();
   }
}
