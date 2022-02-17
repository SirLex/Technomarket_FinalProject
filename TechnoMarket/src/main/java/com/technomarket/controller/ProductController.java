package com.technomarket.controller;


import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.product.ProductRequestDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
/*

    @GetMapping("/product/{id}")
    public ProductResponseDTO getById(@PathVariable int id){
        ProductResponseDTO responseDTO = productService.getById(id);
    }

    @PutMapping("/add/product")
    public ProductResponseDTO addProduct(@Valid @RequestBody ProductRequestDTO productDTO, HttpSession session){
    }

    @DeleteMapping("/product/{id}")
    public MessageDTO deleteProduct(@PathVariable int id, HttpSession session){

    }
*/

}
