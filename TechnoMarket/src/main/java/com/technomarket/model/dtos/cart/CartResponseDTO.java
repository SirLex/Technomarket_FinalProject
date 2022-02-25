package com.technomarket.model.dtos.cart;


import com.technomarket.model.dtos.order.ProductWithQuantityDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartResponseDTO {

    List<ProductWithQuantityDTO> productsWithQuantity;

    public CartResponseDTO(List<ProductWithQuantityDTO> productsWithQuantity) {
        this.productsWithQuantity = productsWithQuantity;
    }
}
