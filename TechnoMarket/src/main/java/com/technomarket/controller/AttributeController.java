package com.technomarket.controller;


import com.technomarket.model.dtos.attribute.AttributeAddDTO;
import com.technomarket.model.dtos.category.CategoryAddDTO;
import com.technomarket.model.dtos.category.CategoryResponseDTO;
import com.technomarket.model.dtos.user.UserResponseDTO;
import com.technomarket.model.pojos.Attributes;
import com.technomarket.model.pojos.Category;
import com.technomarket.model.repositories.AttributeRepository;
import com.technomarket.model.services.AttributeService;
import com.technomarket.model.services.CategoryService;
import com.technomarket.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AttributeController {

    public static final String USER_ID = "user_id";

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private UserService userService;

    @PostMapping("/attribute")
    public ResponseEntity<Attributes> createAttribute(@Valid @RequestBody AttributeAddDTO attributeDTO, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(USER_ID);
        userService.adminValidation(userId);
        Attributes response = attributeService.addAttribute(attributeDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/attribute/{id}")
    public ResponseEntity<Attributes> getAttributeById(@PathVariable int id) {
        Attributes response = attributeService.getById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
