package com.technomarket.model.dtos.review;

import com.technomarket.model.pojos.Review;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewDTO {


    @NotNull
    private String alias;

    @NotNull
    @Length(min = 10)
    private String comment;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

    @NotNull
    private boolean isRecommended;

    public AddReviewDTO(Review review) {

        this.comment = review.getComment();
        this.rating = review.getRating();
    }
}
