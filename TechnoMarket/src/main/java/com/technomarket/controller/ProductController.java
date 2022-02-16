package com.technomarket.controller;


import com.technomarket.model.dtos.ProductDTO;
import com.technomarket.model.dtos.ProductFilteringDTO;
import com.technomarket.model.dtos.ProductWithAllDTO;
import com.technomarket.model.services.ProductService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @SneakyThrows
    @GetMapping("/products/{productId}")
    public ProductWithAllDTO getProduct(@PathVariable(name = "productId") int productId) {
        return productService.getProduct(productId);
    }

    @SneakyThrows
    @GetMapping("/products/search")
    public List<ProductDTO> productsFromSearch(@Valid ProductFilteringDTO ProductFilteringDTO) {
        return productService.productsFromSearch(ProductFilteringDTO);
    }
}
