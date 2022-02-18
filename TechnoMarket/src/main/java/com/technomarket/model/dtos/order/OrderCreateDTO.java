package com.technomarket.model.dtos.order;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreateDTO {
    @NotNull
    private List<ProductWithQuantityDTO> listOfProductsIdAndQuantity;
}
