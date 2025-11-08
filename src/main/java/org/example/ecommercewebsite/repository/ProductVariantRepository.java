package org.example.ecommercewebsite.repository;

import org.example.ecommercewebsite.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant,Long> {
    boolean existsByIdAndStockGreaterThan(Long variantId,int stock);
}
