package com.technomarket.model.dtos.product;


import com.sun.istack.NotNull;
import com.technomarket.model.dtos.attribute.AttributeWithValueDTO;
import com.technomarket.model.dtos.discount.DiscountResponseDTO;
import com.technomarket.model.dtos.subcategory.SubcategoryResponseWithoutCategoryDTO;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.relationentity.ProductAttribute;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

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

    private double priceAfterDiscount;

    @NotBlank
    private String info;

    private DiscountResponseDTO discount;

    private List<AttributeWithValueDTO> attributes;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.subcategory = new SubcategoryResponseWithoutCategoryDTO(product.getSubcategory());
        this.price = product.getPrice();
        this.priceAfterDiscount = product.calculatePriceDiscount();
        this.info = product.getInfo();

        if (product.getDiscount() != null) {
            this.discount = new DiscountResponseDTO(product.getDiscount());
        }

        this.attributes = new ArrayList<>();
        if (product.getProductAttribute() != null) {
            for (ProductAttribute productAttribute : product.getProductAttribute()) {
                attributes.add(new AttributeWithValueDTO(productAttribute));
            }
        }
    }

    @Override
    public String toString() {
        return "ProductResponseDTO{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", priceAfterDiscount=" + priceAfterDiscount +
                ", info='" + info + '\'' +
                '}';
    }
}
