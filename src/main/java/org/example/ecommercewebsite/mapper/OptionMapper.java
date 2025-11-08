package org.example.ecommercewebsite.mapper;

import org.example.ecommercewebsite.dto.request.OptionRequest;
import org.example.ecommercewebsite.dto.request.ValueRequest;
import org.example.ecommercewebsite.dto.response.OptionResponse;
import org.example.ecommercewebsite.dto.response.OptionValueResponse;
import org.example.ecommercewebsite.entity.Option;
import org.example.ecommercewebsite.entity.OptionValue;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    @Mapping(source="values",target="optionValueSet",qualifiedByName = "mapValue")
    Option toEntity(OptionRequest optionRequest);
    @Mapping(source = "optionValueSet",target = "optionValueSet",qualifiedByName = "mapOptionValue")
    OptionResponse toDto(Option option);
    @Named("mapValue")
    static Set<OptionValue> mapValue(List<ValueRequest> valueRequests){
        return valueRequests.stream().map(v ->{
            OptionValue optionValue = new OptionValue();
            optionValue.setValue(v.getLabel());
            return optionValue;
        }).collect(Collectors.toSet());
    }
    @Named("mapOptionValue")
    static OptionValueResponse mapOptionValue(OptionValue optionValue){
        if(optionValue==null) return null;
        OptionValueResponse optionValueResponse = new OptionValueResponse();
        optionValueResponse.setValue(optionValue.getValue());
        return optionValueResponse;
    }
    @AfterMapping
    default void linkedParent(@MappingTarget  Option parent){
        for(OptionValue optionValue: parent.getOptionValueSet()){
            optionValue.setOption(parent);
        }
    }
}
