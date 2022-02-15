package com.technomarket.model.dtos;


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

    private String text;

    private int rating;

    private LocalDate date;


}
