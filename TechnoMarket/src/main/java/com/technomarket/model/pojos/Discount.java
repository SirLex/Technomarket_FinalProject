package com.technomarket.model.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private int discountPercentage;
    @Column
    private LocalDateTime startAt;
    @Column
    private LocalDateTime endAt;

    @OneToMany(mappedBy = "discount")
    private List<Product> products;
}
