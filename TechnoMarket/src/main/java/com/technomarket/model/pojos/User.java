package com.technomarket.model.pojos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private LocalDate dateOfBirth;
    @Column
    private boolean isAdmin;
    @Column
    private boolean isSubscribed;
    @Column
    private boolean isMale;
    @Column
    private boolean verified;
    @Column
    private boolean deleted=false;


    @OneToMany(mappedBy = "user")
    private List<Order> orderList=new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_have_favourites",joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="product_id")})
    private List<Product> favoriteProducts =new ArrayList<>();
}
