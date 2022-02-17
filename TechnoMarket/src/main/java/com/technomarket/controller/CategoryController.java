package com.technomarket.controller;

import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.model.dtos.category.CategoryAddDTO;
import com.technomarket.model.dtos.category.CategoryResponseDTO;
import com.technomarket.model.pojos.Category;
import com.technomarket.model.services.CategoryService;
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
public class CategoryController {

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/category")
    public CategoryResponseDTO createCategory(@Valid @RequestBody CategoryAddDTO categoryDTO, HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(USER_ID);
        userService.adminValidation(userId);
        CategoryResponseDTO responseDTO = categoryService.addCategory(categoryDTO);
        return responseDTO;
    }

    @GetMapping("/category/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable int id) {
        return categoryService.getById(id);
    }

    @GetMapping("/category/all")
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        List<CategoryResponseDTO> responseCategoryDTOList = new ArrayList<>();
        for (Category c : categories) {
            responseCategoryDTOList.add(new CategoryResponseDTO(c));
        }
        return responseCategoryDTOList;
    }
}
