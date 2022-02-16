package com.technomarket.model.dtos;


import com.technomarket.model.pojos.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GetProductReviewDTO {

    private long id;

    private String title;

    private String comment;

    private int rating;

    private LocalDate date;

    public GetProductReviewDTO(Review review) {
        setId(review.getId());
        setComment(review.getComment());
        setRating(review.getRating());
        setDate(review.getDate());
    }
}
