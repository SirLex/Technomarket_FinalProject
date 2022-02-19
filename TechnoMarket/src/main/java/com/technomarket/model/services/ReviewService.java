package com.technomarket.model.services;


import com.technomarket.exceptions.BadRequestException;
import com.technomarket.exceptions.NotFoundException;
import com.technomarket.model.dtos.review.AddReviewDTO;
import com.technomarket.model.dtos.review.ReviewResponseDTO;
import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.pojos.Product;

import com.technomarket.model.pojos.Review;
import com.technomarket.model.pojos.User;
import com.technomarket.model.repositories.ProductRepository;
import com.technomarket.model.repositories.ReviewRepository;
import com.technomarket.model.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service


public class ReviewService  {


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired ProductRepository productRepository;


    @SneakyThrows
    public ReviewResponseDTO addReviewToProduct(int productId, AddReviewDTO addReviewDto, int userId){

        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product not found to add review");
        }
        User user= userRepository.getById(userId);
        Product product=productRepository.getById(productId);

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setAlias(addReviewDto.getAlias());
        review.setRating(addReviewDto.getRating());
        review.setComment(addReviewDto.getComment());
        review.setRecommended(addReviewDto.isRecommended());
        review.setDate(LocalDateTime.now());
        reviewRepository.save(review);
        return new ReviewResponseDTO(review);

    }

    public ReviewResponseDTO getById(int id) {
        if (!reviewRepository.existsById(id)) {
            throw new BadRequestException("Review with this id doesn't exists");
        }
        return new ReviewResponseDTO(reviewRepository.getById(id));
    }


    public MessageDTO deleteReview(int id) {
        if (!reviewRepository.existsById(id)) {
            throw new BadRequestException("Review with this id doesn't exist");
        }
        Review review = reviewRepository.getById(id);
        reviewRepository.delete(review);
        return new MessageDTO("Delete successful", LocalDateTime.now());
    }


    public List<ReviewResponseDTO> getReviewsForProduct(int productId) {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product not found to add review");
        }

        Product product = productRepository.getById(productId);
        List<Review> reviews = reviewRepository.findAllByProduct(product);

        List<ReviewResponseDTO> responseDTOS = new ArrayList<>();
        for (Review review : reviews) {
            responseDTOS.add(new ReviewResponseDTO(review));
        }

        return responseDTOS;
    }

    public List<ReviewResponseDTO> getReviewsForUser(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Product not found to add review");
        }

        User user = userRepository.getById(userId);
        List<Review> reviews = reviewRepository.findAllByUser(user);
        List<ReviewResponseDTO> responseDTOS = new ArrayList<>();
        for (Review review : reviews) {
            responseDTOS.add(new ReviewResponseDTO(review));
        }

        return responseDTOS;
    }
}
