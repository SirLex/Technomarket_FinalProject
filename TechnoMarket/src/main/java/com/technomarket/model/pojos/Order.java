package com.technomarket.model.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private double price;
    @Column
    private String address;
    @Column
    private LocalDate createdAt;
    @Column
    private LocalDate completedAt;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy ="orderList")
    private List<User> userList=new ArrayList<>();



}