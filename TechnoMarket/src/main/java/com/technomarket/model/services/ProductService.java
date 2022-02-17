package com.technomarket.model.services;


import com.technomarket.exceptions.NotFoundException;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.repositories.ProductRepository;
import com.technomarket.model.repositories.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

/*
       public ProductResponseDTO getById(int id) {
        Product product = productRepository.getById(id);
        if(product==null){
            throw new NotFoundException("Product not found");
        }

    }*/
}
