package org.example.ecommercewebsite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "option",cascade = CascadeType.ALL)
    private Set<OptionValue> optionValueSet;
    @ManyToOne
    private Product product;
}
