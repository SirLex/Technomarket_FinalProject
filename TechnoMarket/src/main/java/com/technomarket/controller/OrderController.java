package com.technomarket.controller;

import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.order.OrderResponseDTO;
import com.technomarket.model.dtos.product.ProductFullWithQuantityDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<MessageDTO> createOrderFromCart(HttpServletRequest request) {
        UserController.validateLogin(request);
        int userId = (int) request.getSession().getAttribute(UserController.USER_ID);
        MessageDTO response = orderService.createOrder(request.getSession(), userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/order/{id}/products")
    public ResponseEntity<List<ProductFullWithQuantityDTO>> getProducts(@PathVariable int id, HttpServletRequest request) {
        UserController.validateLogin(request);
        int userId = (int) request.getSession().getAttribute(UserController.USER_ID);

        List<ProductFullWithQuantityDTO> response = orderService.getProducts(id, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable int id, HttpServletRequest request) {
        UserController.validateLogin(request);
        int userId = (int) request.getSession().getAttribute(UserController.USER_ID);
        OrderResponseDTO responseDTO = orderService.getById(id, userId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
