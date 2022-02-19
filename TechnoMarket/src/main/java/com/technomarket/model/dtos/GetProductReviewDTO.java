package com.technomarket.model.dtos;


import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.pojos.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GetProductReviewDTO {
    @NotNull
    private int id;
    @NotNull
    private String alias;
    @NotNull
    private ProductResponseDTO product;

    private String comment;
    @NotNull
    private int rating;
    @NotNull
    private LocalDateTime date;

    public GetProductReviewDTO(Review review) {
        setId(review.getId());
        setAlias(review.getAlias());
        setProduct(new ProductResponseDTO(review.getProduct()));
        setComment(review.getComment());
        setRating(review.getRating());
        setDate(review.getDate());
    }
}
