package com.technomarket.controller;

import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.order.OrderCreateDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.pojos.Order;
import com.technomarket.model.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/order/{id}/products")
    public ResponseEntity<List<ProductResponseDTO>> getProducts(@PathVariable int id, HttpServletRequest request) {
        UserController.validateLogin(request);
        int userId = (int) request.getSession().getAttribute(UserController.USER_ID);

        List<ProductResponseDTO> response = orderService.getProducts(id, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public Order getById(@PathVariable int id, HttpServletRequest request) {
        UserController.validateLogin(request);
        int userId = (int) request.getSession().getAttribute(UserController.USER_ID);

        return orderService.getById(id, userId);
    }

}
