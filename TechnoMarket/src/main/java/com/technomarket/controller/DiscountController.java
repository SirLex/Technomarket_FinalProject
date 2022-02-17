package com.technomarket.controller;

import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.category.CategoryResponseDTO;
import com.technomarket.model.dtos.discount.DiscountAddDTO;
import com.technomarket.model.pojos.Discount;
import com.technomarket.model.services.DiscountService;
import com.technomarket.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@Validated
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;


    @PostMapping("/discount")
    public ResponseEntity<Discount> addDiscount(@Valid @RequestBody DiscountAddDTO dto, HttpServletRequest request){
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        userService.adminValidation(userId);

        Discount response = discountService.addDiscount(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/discount/{id}")
    public ResponseEntity<MessageDTO> editDiscountById(@Valid @RequestBody DiscountAddDTO dto, @PathVariable int id, HttpServletRequest request){
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        userService.adminValidation(userId);

        Discount response = discountService.editDiscount(dto,id);
        return new ResponseEntity<>(new MessageDTO("Change is made", LocalDateTime.now()),HttpStatus.ACCEPTED);
    }

    @GetMapping("/discount/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable int id){
        Discount response = discountService.getDiscountById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
