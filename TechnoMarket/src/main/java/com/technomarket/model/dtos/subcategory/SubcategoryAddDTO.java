package com.technomarket.model.dtos.subcategory;

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
public class SubcategoryAddDTO {

    @NotBlank
    private String name;

    @NotNull
    private int categoryId;
}