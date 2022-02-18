package com.technomarket.model.pojos;

import com.technomarket.model.relationentity.OrderProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter

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

    @OneToMany(mappedBy = "order")
    private Set<OrderProduct> orderProducts;

    public Order(){
        this.orderProducts = new HashSet<>();
    }

}