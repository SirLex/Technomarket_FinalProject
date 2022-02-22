package com.technomarket.model.event;

import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ProductDiscountListener implements ApplicationListener<OnProductDiscountEvent> {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnProductDiscountEvent event) {
        Product product = event.getProduct();
        for (User user : product.getUserList()) {
            if(user.isSubscribed()){
                SimpleMailMessage email = new SimpleMailMessage();
                email.setTo(user.getEmail());
                email.setSubject("Favorite product is on discount");
                email.setText("\r\n" + new ProductResponseDTO(product).toString());
                mailSender.send(email);
            }
        }
    }
}
