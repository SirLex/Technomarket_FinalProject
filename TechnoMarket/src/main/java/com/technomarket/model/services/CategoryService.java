package com.technomarket.model.services;


import com.technomarket.exceptions.BadRequestException;
import com.technomarket.model.dtos.category.CategoryAddDTO;
import com.technomarket.model.dtos.category.CategoryResponseDTO;
import com.technomarket.model.pojos.Category;
import com.technomarket.model.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponseDTO addCategory(CategoryAddDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new BadRequestException("Category already exists");
        }
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return new CategoryResponseDTO(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryResponseDTO getById(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new BadRequestException("Category with this id doesn't exist");
        }
        return new CategoryResponseDTO(categoryRepository.getById(id));
    }
}
