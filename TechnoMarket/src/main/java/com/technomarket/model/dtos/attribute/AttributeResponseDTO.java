package com.technomarket.model.dtos.attribute;

import com.technomarket.model.pojos.Attributes;
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
public class AttributeResponseDTO {

    @NotNull
    private int id;

    @NotBlank
    private String name;

    public AttributeResponseDTO(Attributes attribute) {
        this.id = attribute.getId();
        this.name = attribute.getName();
    }
}
