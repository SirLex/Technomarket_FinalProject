package com.technomarket.model.dtos.attribute;

import com.technomarket.model.relationentity.ProductAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@Setter
@Getter
@NoArgsConstructor
public class AttributeWithValueDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String value;

    public AttributeWithValueDTO(ProductAttribute a) {
        this.name = a.getAttribute().getName();
        this.value = a.getValue();
    }
}
