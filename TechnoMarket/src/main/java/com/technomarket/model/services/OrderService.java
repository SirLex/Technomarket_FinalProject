package com.technomarket.model.services;


import com.technomarket.exceptions.BadRequestException;
import com.technomarket.model.dtos.order.OrderDTO;
import com.technomarket.model.pojos.Order;
import com.technomarket.model.repositories.OrderRepository;
import com.technomarket.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;


    public Order getById(int id) {
        if (!orderRepository.existsById(id)) {
            throw new BadRequestException("Order with this id doesn't exist");
        }
        return orderRepository.getById(id);
    }


    public List<OrderDTO> getAllOrdersFromUser(int userId) {

        if (!userRepository.existsById(userId)) {
            throw new BadRequestException("User does not exist");
        }
        return orderRepository.findAllByUserId(userId).stream().map(OrderDTO::new).collect(Collectors.toList());

    }
}
