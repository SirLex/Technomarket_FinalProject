package com.technomarket.model.repositories;

import com.technomarket.model.pojos.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUser_Id(int userId);
}
