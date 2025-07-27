package org.example.ecommercewebsite.repository;

import org.example.ecommercewebsite.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByName(String name);

    List<Product> findAllBySeller_id(Long sellerId);
    @Query(value = "select p from Product p where (:minPrice is null or :minPrice <=p.price) and (:maxPrice is null or p.price >=:maxPrice) and (:category is null or p.category.id=:category)")
    List<Product> filterProduct(BigDecimal minPrice, BigDecimal maxPrice, Long category);
}
