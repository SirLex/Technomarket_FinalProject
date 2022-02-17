package com.technomarket.model.repositories;

import com.technomarket.model.pojos.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    boolean existsByTitle(String title);
}
