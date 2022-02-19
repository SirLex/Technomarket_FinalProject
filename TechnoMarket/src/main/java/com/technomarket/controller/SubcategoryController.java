package com.technomarket.controller;

import com.technomarket.model.dtos.category.CategoryResponseDTO;
import com.technomarket.model.dtos.subcategory.SubcategoryAddDTO;
import com.technomarket.model.dtos.subcategory.SubcategoryResponseDTO;
import com.technomarket.model.pojos.Subcategory;
import com.technomarket.model.services.SubcategoryService;
import com.technomarket.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
public class SubcategoryController {

    public static final String USER_ID = "user_id";

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/subcategory")
    public ResponseEntity<SubcategoryResponseDTO> createSubcategory(@Valid @RequestBody SubcategoryAddDTO subcategoryDTO, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(USER_ID);
        userService.adminValidation(userId);
        SubcategoryResponseDTO responseDTO = subcategoryService.addSubcategory(subcategoryDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/subcategory/{subId}/addatribute/{attId}")
    public ResponseEntity<SubcategoryResponseDTO> addAttributeToAllowed(@PathVariable int subId, @PathVariable int attId, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(USER_ID);
        userService.adminValidation(userId);

        SubcategoryResponseDTO responseDTO = subcategoryService.addAttributeToAllowed(subId,attId);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/subcategory/{id}")
    public ResponseEntity<SubcategoryResponseDTO> getSubcategoryById(@PathVariable int id) {
        SubcategoryResponseDTO responseDTO = subcategoryService.getById(id);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @GetMapping("/subcategory/all")
    public ResponseEntity<List<CategoryResponseDTO>> getAllSubcategories() {
        List<CategoryResponseDTO> responseDTO = subcategoryService.getAllCategoriesWithSubCategories();
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}

