package com.technomarket.model.dtos;

import com.technomarket.model.dtos.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;

    private String title;

    private String text;

    private int rating;

    private LocalDate date;

    private UserDTO user;
}
