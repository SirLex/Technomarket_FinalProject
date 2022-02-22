package com.technomarket.model.event;

import com.technomarket.model.pojos.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnProductDiscountEvent extends ApplicationEvent {

    private Product product;
    private Locale locale;

    public OnProductDiscountEvent(Product product) {
        super(product);
        this.product=product;
    }
}
