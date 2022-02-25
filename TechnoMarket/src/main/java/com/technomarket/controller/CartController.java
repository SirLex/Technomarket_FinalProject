package com.technomarket.controller;

import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.cart.CartResponseDTO;
import com.technomarket.model.dtos.order.ProductWithQuantityDTO;
import com.technomarket.model.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping("/cart")
    public ResponseEntity<CartResponseDTO> addProductToCart(@Valid @RequestBody ProductWithQuantityDTO dto, HttpSession session) {
        CartResponseDTO response = cartService.addProductToCart(dto, session);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/cart/{productId}")
    public ResponseEntity<MessageDTO> removeProductFromCart(@PathVariable int productId, HttpSession session) {
        MessageDTO response = cartService.removeProductFromCart(productId, session);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/cart")
    public ResponseEntity<CartResponseDTO> showCart(HttpSession session) {
        CartResponseDTO response = cartService.showCart(session);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
