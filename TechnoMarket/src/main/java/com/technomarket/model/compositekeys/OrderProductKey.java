package com.technomarket.model.compositekeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderProductKey implements Serializable {

    @Column(name="order_id")
    int orderId;

    @Column(name="product_id")
    int productId;
}