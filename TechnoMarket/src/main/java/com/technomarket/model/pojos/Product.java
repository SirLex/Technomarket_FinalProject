package com.technomarket.model.pojos;

import com.technomarket.model.dtos.product.ProductResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String brand;
    @Column
    private int subcategoryID;
    @Column
    private double price;
    @Column
    private String info;
    @Column
    private int discountID;


    public Product(ProductResponseDTO productDTO) {

        this.name=productDTO.getName();

    }
}
