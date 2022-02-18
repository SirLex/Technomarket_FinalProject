package com.technomarket.model.dtos.product;


import com.sun.istack.NotNull;
import com.technomarket.model.dtos.discount.DiscountResponseDTO;
import com.technomarket.model.dtos.subcategory.SubcategoryResponseWithoutCategoryDTO;
import com.technomarket.model.pojos.Discount;
import com.technomarket.model.pojos.Product;
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
    private SubcategoryResponseWithoutCategoryDTO subcategory;

    @NotNull
    private double price;

    @NotBlank
    private String info;

    private DiscountResponseDTO discount;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.subcategory = new SubcategoryResponseWithoutCategoryDTO(product.getSubcategory());
        this.price = product.getPrice();
        this.info = product.getInfo();
        if (product.getDiscount() != null) {
            this.discount = new DiscountResponseDTO(product.getDiscount());
        }
    }
}
