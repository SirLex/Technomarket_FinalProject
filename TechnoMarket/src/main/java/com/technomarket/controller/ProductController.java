package com.technomarket.controller;


import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.attribute.AttributeAddValueToProductDTO;
import com.technomarket.model.dtos.product.ProductAddDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.services.ProductService;
import com.technomarket.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @PostMapping("/product")
    public ResponseEntity<ProductResponseDTO> addProduct(@Valid @RequestBody ProductAddDTO productDTO, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        userService.adminValidation(userId);

        ProductResponseDTO response = productService.addProduct(productDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/product/{productId}/discount/{discountId}")
    public ResponseEntity<ProductResponseDTO> addDiscountToProduct(@PathVariable int productId, @PathVariable int discountId, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        userService.adminValidation(userId);

        ProductResponseDTO responseDTO = productService.addDiscountToProduct(productId, discountId);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("/product/{productId}/addattribute/{attId}")
    public ResponseEntity<ProductResponseDTO> addAttributeToProduct(@RequestBody AttributeAddValueToProductDTO dto, @PathVariable int productId, @PathVariable int attId, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        userService.adminValidation(userId);

        ProductResponseDTO response = productService.addAttributeToProduct(dto,productId,attId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<MessageDTO> deleteProduct(@PathVariable int id, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        userService.adminValidation(userId);

        MessageDTO response = productService.deleteProduct(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{id}")
    public Product getById(@PathVariable int id) {
        return productService.getById(id);
    }

}
