package com.technomarket.controller;


import com.technomarket.model.dtos.attribute.AttributeAddDTO;
import com.technomarket.model.dtos.attribute.AttributeResponseDTO;
import com.technomarket.model.services.AttributeService;
import com.technomarket.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class AttributeController {

    public static final String USER_ID = "user_id";

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private UserService userService;

    @PostMapping("/attribute")
    public ResponseEntity<AttributeResponseDTO> createAttribute(@Valid @RequestBody AttributeAddDTO attributeDTO, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(USER_ID);
        userService.adminValidation(userId);
        AttributeResponseDTO response = attributeService.addAttribute(attributeDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/attribute/{id}")
    public ResponseEntity<AttributeResponseDTO> getAttributeById(@PathVariable int id) {
        AttributeResponseDTO response = attributeService.getById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
