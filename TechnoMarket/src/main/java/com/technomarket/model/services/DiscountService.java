package com.technomarket.model.services;

import com.technomarket.exceptions.BadRequestException;
import com.technomarket.model.dtos.discount.DiscountAddDTO;
import com.technomarket.model.pojos.Discount;
import com.technomarket.model.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    @Transactional
    public Discount addDiscount(DiscountAddDTO dto) {
        if (discountRepository.existsByTitle(dto.getTitle())) {
            throw new BadRequestException("Discount with this title already exists");
        }
        Discount discount = new Discount();
        discount.setTitle(dto.getTitle());
        discount.setDiscountPercentage(dto.getDiscountPercentage());
        discount.setStartAt(dto.getStartAt());
        discount.setEndAt(dto.getEndAt());
        discountRepository.save(discount);
        return discount;
    }

    public Discount getDiscountById(int id) {
        if(!discountRepository.existsById(id)){
            throw new BadRequestException("Discount with this id doesn't exist");
        }
        return discountRepository.getById(id);
    }

    @Transactional
    public Discount editDiscount(DiscountAddDTO dto, int id) {
        if(!discountRepository.existsById(id)){
            throw new BadRequestException("Discount with this id doesn't exist");
        }
        Discount discount = discountRepository.getById(id);
        System.out.println(discount);
        discount.setTitle(dto.getTitle());
        discount.setDiscountPercentage(dto.getDiscountPercentage());
        discount.setStartAt(dto.getStartAt());
        discount.setEndAt(dto.getEndAt());
        discountRepository.save(discount);
        return discount;
    }
}