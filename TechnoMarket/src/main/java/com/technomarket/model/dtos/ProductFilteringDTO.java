package com.technomarket.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductFilteringDTO {

    @Min(0)
    private Long subCategoryId;
    @Min(0)
    private BigDecimal minPrice;
    @Min(0)
    private BigDecimal maxPrice;

}
