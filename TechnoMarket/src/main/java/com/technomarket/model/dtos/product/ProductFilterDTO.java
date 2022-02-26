package com.technomarket.model.dtos.product;


import com.sun.istack.NotNull;
import com.technomarket.model.dtos.attribute.AttributeFilterDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDTO {

    @NotNull
    @Min(0)
    private int subcategoryId;

    List<String> listOfBrands;

    @NotNull
    @Min(0)
    private double min;

    @NotNull
    @Min(0)
    @Max(10000)
    private double max;

    private boolean onDiscount;


    List<@Valid AttributeFilterDTO> attributeFilterDTOList;

    private String orderBy;
}
