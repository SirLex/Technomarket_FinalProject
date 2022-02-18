package com.technomarket.controller;

import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.order.OrderCreateDTO;
import com.technomarket.model.dtos.order.OrderDTO;
import com.technomarket.model.dtos.order.ProductWithQuantityDTO;
import com.technomarket.model.pojos.Order;
import com.technomarket.model.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<MessageDTO> createOrderByListOfProducts(@RequestBody OrderCreateDTO dto, HttpServletRequest request) {
        UserController.validateLogin(request);
        int userId = (int) request.getSession().getAttribute(UserController.USER_ID);
        MessageDTO response = orderService.createOrder(dto, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/order")
    public OrderCreateDTO test() {
        OrderCreateDTO dto = new OrderCreateDTO();
        List<ProductWithQuantityDTO> a = new ArrayList<>();
        a.add(new ProductWithQuantityDTO());
        a.add(new ProductWithQuantityDTO());
        dto.setListOfProductsIdAndQuantity(a);
        return dto;
    }

    @GetMapping("/order/{id}")
    public Order getById(@PathVariable int id) {
        return orderService.getById(id);
    }

}
