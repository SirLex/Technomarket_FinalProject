package com.technomarket.model.dtos.attribute;


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

public class AttributeFilterDTO {

    @NotNull(message = "Attribute Filter must not be null")
    private int id;

    @NotBlank(message = "Attribute Filter must not be blank")
    private String value;

}