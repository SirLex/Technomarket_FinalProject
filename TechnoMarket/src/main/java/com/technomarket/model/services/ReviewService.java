package com.technomarket.model.services;


import com.technomarket.exceptions.NotFoundException;
import com.technomarket.model.dtos.AddReviewDTO;
import com.technomarket.model.dtos.GetProductReviewDTO;
import com.technomarket.model.pojos.Product;

import com.technomarket.model.pojos.User;
import com.technomarket.model.repositories.ProductRepository;
import com.technomarket.model.repositories.ReviewRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service


public class ReviewService  {


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;


    @SneakyThrows
    public GetProductReviewDTO addReviewToProduct(int productId, AddReviewDTO addReviewDto, User user){
       // checkForLoggedUser(user);
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("No such Product"));

        //TODO setreview
        return null;

    }


}
