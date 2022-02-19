package com.technomarket.controller;

import com.technomarket.model.dtos.discount.DiscountAddDTO;
import com.technomarket.model.dtos.discount.DiscountResponseDTO;
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

@RestController
@Validated
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;


    @PostMapping("/discount")
    public ResponseEntity<Discount> addDiscount(@Valid @RequestBody DiscountAddDTO dto, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        userService.adminValidation(userId);

        Discount response = discountService.addDiscount(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/discount/{id}")
    public ResponseEntity<DiscountResponseDTO> editDiscountById(@Valid @RequestBody DiscountAddDTO dto, @PathVariable int id, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        userService.adminValidation(userId);

        DiscountResponseDTO response = discountService.editDiscount(dto, id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/discount/{id}")
    public ResponseEntity<DiscountResponseDTO> getDiscountById(@PathVariable int id) {
        DiscountResponseDTO response = discountService.getDiscountById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
