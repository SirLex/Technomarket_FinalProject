package com.technomarket.model.dtos.order;

import com.sun.istack.NotNull;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddProductsToOrder {
    @NotNull
    private OrderDTO order;
    @NotNull
    private List<ProductResponseDTO> products;
}
