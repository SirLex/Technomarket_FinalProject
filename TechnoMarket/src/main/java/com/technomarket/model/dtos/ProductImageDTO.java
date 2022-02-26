package com.technomarket.model.dtos;


import com.technomarket.model.pojos.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO {

    @NotNull
    private int id;

    @NotNull
    private String url;

    @NotNull
    private int productId;


    public ProductImageDTO(ProductImage productImage) {
        this.id=productImage.getId();
        this.url=productImage.getUrl();
        this.productId=productImage.getProduct().getId();
    }

}
