package com.technomarket.model.dtos.product;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductAddDTO {

    @NotBlank
    @Length(min = 2,max = 50,message = "Product name must be between 2 and 100 characters")
    private String name;

    @NotBlank
    @Length(min = 2,max = 50,message = "Brand name must be between 2 and 50 characters")
    private String brand;

    @NotNull
    private int subcategoryId;

    @NotNull
    @Min(1)
    private double price;

    @NotBlank
    private String info;

    private int discountId;
}