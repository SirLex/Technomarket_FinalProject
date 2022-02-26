package com.technomarket.model.dtos.product;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductAddDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    @NotNull
    private int subcategoryId;

    @NotNull
    private double price;

    @NotBlank
    private String info;

    private int discountId;
}