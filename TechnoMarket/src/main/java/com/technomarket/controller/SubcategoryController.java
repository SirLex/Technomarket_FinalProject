package com.technomarket.controller;

import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.model.dtos.category.CategoryAddDTO;
import com.technomarket.model.dtos.category.CategoryResponseDTO;
import com.technomarket.model.dtos.subcategory.SubcategoryAddDTO;
import com.technomarket.model.dtos.subcategory.SubcategoryResponseDTO;
import com.technomarket.model.pojos.Category;
import com.technomarket.model.pojos.Subcategory;
import com.technomarket.model.services.CategoryService;
import com.technomarket.model.services.SubcategoryService;
import com.technomarket.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/subcategory")
    public SubcategoryResponseDTO createSubcategory(@Valid @RequestBody SubcategoryAddDTO subcategoryDTO, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(USER_ID);
        userService.adminValidation(userId);
        SubcategoryResponseDTO responseDTO = subcategoryService.addSubcategory(subcategoryDTO);
        return responseDTO;
    }

    @GetMapping("/subcategory/{id}")
    public SubcategoryResponseDTO getSubcategoryById(@PathVariable int id) {
        return subcategoryService.getById(id);
    }

    @GetMapping("/subcategory/all")
    public List<SubcategoryResponseDTO> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryService.getAllSubcategories();

        List<SubcategoryResponseDTO> responseSubcategoryDTOList = new ArrayList<>();
        for (Subcategory sc : subcategories) {
            responseSubcategoryDTOList.add(new SubcategoryResponseDTO(sc));
        }
        return responseSubcategoryDTOList;
    }
}

