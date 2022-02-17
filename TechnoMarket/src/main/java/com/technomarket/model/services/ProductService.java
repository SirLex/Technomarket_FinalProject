package com.technomarket.model.services;


import com.technomarket.model.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ReviewRepository reviewRepository;

}
