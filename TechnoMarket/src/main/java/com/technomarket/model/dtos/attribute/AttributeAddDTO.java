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
public class AttributeAddDTO {

    @NotBlank
    private String name;
}
