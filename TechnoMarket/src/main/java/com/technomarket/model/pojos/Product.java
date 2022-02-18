package com.technomarket.model.pojos;

import com.technomarket.model.relationentity.ProductAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String brand;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private Subcategory subcategory;

    @Column
    private double price;
    @Column
    private String info;
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @OneToMany(mappedBy="product")
    Set<ProductAttribute> productAttribute;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "productList")
    private List<User> userList ;

}
