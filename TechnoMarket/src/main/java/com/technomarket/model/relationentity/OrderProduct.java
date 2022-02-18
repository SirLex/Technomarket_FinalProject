package com.technomarket.model.relationentity;


import com.technomarket.model.compositekeys.OrderProductKey;
import com.technomarket.model.compositekeys.ProductAttributeKey;
import com.technomarket.model.pojos.Attributes;
import com.technomarket.model.pojos.Order;
import com.technomarket.model.pojos.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders_have_products")
public class OrderProduct {

    @EmbeddedId
    OrderProductKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name="order_id")
    Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id")
    Product product;

    int quantity;

}