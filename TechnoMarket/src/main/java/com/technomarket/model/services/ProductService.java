package com.technomarket.model.services;


import com.technomarket.exceptions.BadRequestException;
import com.technomarket.exceptions.NotFoundException;
import com.technomarket.model.dtos.product.ProductAddDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.repositories.ProductRepository;
import com.technomarket.model.repositories.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SubcategoryService subcategoryService;

    public ProductResponseDTO addProduct(ProductAddDTO productDTO) {
        if(productRepository.existsByName(productDTO.getName())){
           throw new BadRequestException("Product with that name already exists");
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setSubcategory(subcategoryService.getWholeById(productDTO.getSubcategoryId()));
        product.setPrice(productDTO.getPrice());
        product.setInfo(productDTO.getInfo());
        //TODO WITH DISCOUNT!!!!!!!!!!!!!!!!!!!!!!!!!
        product.setDiscountId(1);

        productRepository.save(product);

        return new ProductResponseDTO(product);
    }

    public Product getById(int id) {
        if(!productRepository.existsById(id)){
            throw new BadRequestException("Product with this id doesn't exist");
        }
        return productRepository.getById(id);
    }

/*
       public ProductResponseDTO getById(int id) {
        Product product = productRepository.getById(id);
        if(product==null){
            throw new NotFoundException("Product not found");
        }

    }*/
}
