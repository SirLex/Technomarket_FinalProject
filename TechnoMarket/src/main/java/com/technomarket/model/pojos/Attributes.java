package com.technomarket.model.pojos;

import com.technomarket.model.relationentity.ProductAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "attributes")
@Getter
@Setter
@NoArgsConstructor
public class Attributes {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    @ManyToMany(mappedBy = "allowedAttributes")
    Set<Subcategory> subcategories;

    @OneToMany(mappedBy = "attribute")
    Set<ProductAttribute> productAttribute;

}
