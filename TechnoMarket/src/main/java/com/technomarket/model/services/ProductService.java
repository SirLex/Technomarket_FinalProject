package com.technomarket.model.services;


import com.technomarket.exceptions.NotFoundException;
import com.technomarket.model.dtos.ProductDTO;
import com.technomarket.model.dtos.ProductFilteringDTO;
import com.technomarket.model.dtos.ProductWithAllDTO;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ReviewRepository reviewRepository;


    protected void checkForProductExistence(Product product) throws NotFoundException {
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
    }

    public ProductWithAllDTO getProduct(long productId) {

        //TODO
        return null;
    }

    public List<ProductDTO> productsFromSearch(ProductFilteringDTO productFilteringDTO) {

        //TODO
        return null;
    }
}
