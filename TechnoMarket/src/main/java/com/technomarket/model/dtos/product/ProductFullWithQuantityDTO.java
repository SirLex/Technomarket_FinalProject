package com.technomarket.model.dtos.product;

import com.sun.istack.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFullWithQuantityDTO {

    @NotNull
    private ProductResponseDTO productInfo;

    private int quantity;

}
