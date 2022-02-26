package com.technomarket.model.dtos.attribute;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@Setter
@Getter
@NoArgsConstructor

public class AttributeFilterDTO {

    @NotBlank
    private int id;

    @NotBlank
    private String value;

}