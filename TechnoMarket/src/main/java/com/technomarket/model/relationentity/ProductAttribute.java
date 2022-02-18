package com.technomarket.model.relationentity;

import com.technomarket.model.compositekeys.ProductAttributeKey;
import com.technomarket.model.pojos.Attributes;
import com.technomarket.model.pojos.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products_have_attributes")
public class ProductAttribute {

    @EmbeddedId
    ProductAttributeKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id")
    Product product;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name="attribute_id")
    Attributes attribute;

    String value;

}
