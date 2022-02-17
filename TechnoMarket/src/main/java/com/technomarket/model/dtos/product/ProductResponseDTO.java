package com.technomarket.model.dtos.product;


import com.sun.istack.NotNull;
import com.technomarket.model.pojos.Discount;
import com.technomarket.model.pojos.Subcategory;
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

public class ProductResponseDTO {

    @NotNull
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    @NotNull
    private Subcategory subcategory;

    @NotNull
    private double price;

    @NotBlank
    private String info;


    private Discount discount;
}
