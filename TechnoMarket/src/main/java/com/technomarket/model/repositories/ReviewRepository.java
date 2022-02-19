package com.technomarket.model.repositories;


import com.technomarket.model.pojos.Product;
import com.technomarket.model.pojos.Review;
import com.technomarket.model.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findAllByProduct(Product product);
    List<Review> findAllByUser(User user);
}