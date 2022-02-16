package com.technomarket.controller;


import com.technomarket.model.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController




public class ReviewController {

    @Autowired
    private ReviewService reviewService;
}
