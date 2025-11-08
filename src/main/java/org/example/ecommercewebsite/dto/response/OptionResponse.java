package org.example.ecommercewebsite.dto.response;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommercewebsite.entity.OptionValue;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionResponse {
    private String name;
    private Set<OptionValueResponse> optionValueSet;
}
