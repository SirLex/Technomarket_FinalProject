package com.technomarket.model.dtos.product;


import com.sun.istack.NotNull;
import com.technomarket.model.dtos.discount.DiscountResponseDTO;
import com.technomarket.model.dtos.subcategory.SubcategoryResponseWithoutCategoryDTO;
import com.technomarket.model.pojos.Discount;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.pojos.Subcategory;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFullWithQuantityDTO {

    @NotNull
    private ProductResponseDTO productInfo;

    private int quantity;

}
