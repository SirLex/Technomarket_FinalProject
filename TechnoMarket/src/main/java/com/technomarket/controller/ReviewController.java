package com.technomarket.controller;


import com.technomarket.model.dtos.review.AddReviewDTO;

import com.technomarket.model.dtos.review.ReviewResponseDTO;
import com.technomarket.model.services.ReviewService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @SneakyThrows
    @PostMapping("/product/{productId}/review")
    public ResponseEntity<ReviewResponseDTO> addReviewToProduct(@Valid @RequestBody AddReviewDTO addReviewDTO, HttpServletRequest request, @PathVariable int productId) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        ReviewResponseDTO reviewResponseDTO = reviewService.addReviewToProduct(productId, addReviewDTO, userId);
        return new ResponseEntity<>(reviewResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsForProduct(@PathVariable int productId) {

        List<ReviewResponseDTO> responseDTOS = reviewService.getReviewsForProduct(productId);
        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }

    @GetMapping("/user/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByUser(HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);

        List<ReviewResponseDTO> responseDTOS = reviewService.getReviewsForUser(userId);
        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }
}
