package org.example.ecommercewebsite.dto.request;

import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OptionRequest {
    private String name;
    private List<ValueRequest> values;
}
