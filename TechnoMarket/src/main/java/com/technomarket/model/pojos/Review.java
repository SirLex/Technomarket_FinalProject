package com.technomarket.model.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "productId", nullable = false)
    private int productId;

    @Column(name = "rating", nullable = false)
    private int rating;


    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name="isRecommended")
    private boolean isRecommended;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}
