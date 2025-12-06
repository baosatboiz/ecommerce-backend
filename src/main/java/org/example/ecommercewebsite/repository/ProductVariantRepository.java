package org.example.ecommercewebsite.repository;

import org.example.ecommercewebsite.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant,Long> {
    boolean existsByIdAndStockGreaterThan(Long variantId,int stock);
    @Modifying
    @Query("update ProductVariant p set p.stock = p.stock-:quantity where p.id=:variantId and p.stock>=:quantity")
    int decreseProductStock(Long variantId, int quantity);
}
