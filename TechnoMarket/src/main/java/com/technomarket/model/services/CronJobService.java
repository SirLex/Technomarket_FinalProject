package com.technomarket.model.services;

import com.technomarket.model.pojos.Discount;
import com.technomarket.model.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Configuration
@EnableScheduling
public class CronJobService {

    @Autowired
    private DiscountRepository discountRepository;

    @Scheduled(cron = "0 0 * * * ?")
    //@Scheduled(fixedDelay = 10000)
    public void invalidateExpiredDiscounts() {
        List<Discount> discounts = discountRepository.findAll();
        for (Discount discount : discounts) {
            if (discount.getEndAt().isBefore(LocalDateTime.now())) {
                discountRepository.delete(discount);
            }
        }
    }
}
