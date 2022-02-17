package com.technomarket.model.dtos.category;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@Setter
@Getter
@NoArgsConstructor
public class CategoryAddDTO {

    @NotBlank
    private String name;

}