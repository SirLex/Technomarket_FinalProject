package com.technomarket.model.pojos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", nullable = false, length = 45)
    private String city;


    @Column(name = "district", nullable = false, length = 45)
    private String district;


    @Column(name = "street", nullable = false, length = 45)
    private String street;


    @Column(name = "zip", nullable = false, length = 45)
    private String zip;


    @Column(name = "phoneNumber", nullable = false, length = 45)
    private String phoneNumber;


}
