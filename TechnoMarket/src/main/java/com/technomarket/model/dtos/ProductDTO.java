package com.technomarket.model.dtos;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDTO {

    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal price;

    private Integer discount;

    private Long subCategoryId;
}
