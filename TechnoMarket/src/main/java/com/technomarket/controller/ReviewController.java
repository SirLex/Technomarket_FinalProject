package com.technomarket.controller;


import com.technomarket.model.dtos.review.AddReviewDTO;

import com.technomarket.model.dtos.review.ReviewResponseDTO;
import com.technomarket.model.services.ReviewService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @SneakyThrows
    @PostMapping("/products/{productId}/review")
    public ResponseEntity<ReviewResponseDTO> addReviewToProduct (@Valid @RequestBody AddReviewDTO addReviewDTO, HttpServletRequest request, @PathVariable int productId)
    {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        ReviewResponseDTO reviewResponseDTO = reviewService.addReviewToProduct(productId,addReviewDTO,userId);
        return new ResponseEntity<>(reviewResponseDTO, HttpStatus.CREATED);
    }
}
