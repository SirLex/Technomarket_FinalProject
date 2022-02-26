package com.technomarket.controller;


import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.attribute.AttributeAddValueToProductDTO;
import com.technomarket.model.dtos.product.ProductAddDTO;
import com.technomarket.model.dtos.product.ProductFilterDTO;
import com.technomarket.model.dtos.product.ProductKeywordsDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.services.ProductService;
import com.technomarket.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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

    @PostMapping("/product/{productId}/attribute/{attId}")
    public ResponseEntity<ProductResponseDTO> addAttributeToProduct(@Valid @RequestBody AttributeAddValueToProductDTO dto, @PathVariable int productId, @PathVariable int attId, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        userService.adminValidation(userId);

        ProductResponseDTO response = productService.addAttributeToProduct(dto, productId, attId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/product/{productId}/attribute/{attId}")
    public ResponseEntity<MessageDTO> deleteAttributeFromProduct(@PathVariable int productId, @PathVariable int attId, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        userService.adminValidation(userId);

        MessageDTO response = productService.deleteAttributeFromProduct(productId, attId);
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

    @PostMapping("/product/{productId}/uploadImage")
    public ResponseEntity<MessageDTO> uploadImage(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request, @PathVariable int productId) throws IOException {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        userService.adminValidation(userId);
        MessageDTO messageDTO = productService.uploadImageToProduct(file, productId);
        return new ResponseEntity<>(messageDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{productId}/images")
    public ResponseEntity<MessageDTO> getImages(@PathVariable int productId, HttpServletResponse response) throws IOException {
        MessageDTO messageDTO = productService.getImages(productId,response);
        return new ResponseEntity<>(messageDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable int id) {
        ProductResponseDTO responseDTO = productService.getById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProductWithFilters(@Valid @RequestBody ProductFilterDTO productFilterDTO) {
        List<ProductResponseDTO> responseDTOS = productService.searchWithFilters(productFilterDTO);
        return new ResponseEntity<>(responseDTOS, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/search/keywords")
    public ResponseEntity<List<ProductResponseDTO>> searchProductWithKeywords(@Valid @RequestBody ProductKeywordsDTO dto) {
        List<ProductResponseDTO> responseDTOS = productService.searchWithKeywords(dto);
        return new ResponseEntity<>(responseDTOS, HttpStatus.ACCEPTED);

    }

}
