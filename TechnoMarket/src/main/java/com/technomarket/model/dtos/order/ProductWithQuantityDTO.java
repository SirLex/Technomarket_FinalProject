package com.technomarket.model.dtos.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ProductWithQuantityDTO {
    @NotNull
    int productId;
    @NotNull
    @Min(1)
    int quantity;
}
