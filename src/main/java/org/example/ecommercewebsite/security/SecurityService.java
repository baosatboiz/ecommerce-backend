package org.example.ecommercewebsite.security;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.entity.Role;
import org.example.ecommercewebsite.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {
    private final ProductRepository productRepository;
    public boolean isProductOwner(Long productId, Long sellerId,Role userRole) {
        return productRepository.findById(productId)
                .map(p->p.getSeller().getId().equals(sellerId)||userRole.equals(Role.ADMIN))
                .orElse(false);
    }
}
