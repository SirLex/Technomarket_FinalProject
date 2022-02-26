package com.technomarket.model.dtos.order;

import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.pojos.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ProductsInOrderResponseDTO {
    @NotNull
    ProductResponseDTO product;

    @NotNull
    @Min(1)
    @Max(100)
    int quantity;

    public ProductsInOrderResponseDTO(Product prod, int quantity) {
        this.product = new ProductResponseDTO(prod);
        this.quantity = quantity;
    }
}