package org.example.ecommercewebsite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    private Option option;
    @ManyToMany(mappedBy = "optionValueSet")
    private List<ProductVariant> variants;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OptionValue that)) return false;
        return value.equals(that.value) &&
                (option != null && that.option != null && option.getId().equals(that.option.getId()));
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (option != null && option.getId() != null ? option.getId().hashCode() : 0);
        return result;
    }
}
