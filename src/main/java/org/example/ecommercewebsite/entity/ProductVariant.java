package org.example.ecommercewebsite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int stock;
    private BigDecimal price;
    private String imageUrl;
    @ManyToMany
    @JoinTable(
            name="variant_option_value",
            joinColumns = @JoinColumn(name="variant_id"),
            inverseJoinColumns = @JoinColumn(name="option_value_id")
    )
    private Set<OptionValue> optionValueSet;
    @ManyToOne
    private Product product;
}
