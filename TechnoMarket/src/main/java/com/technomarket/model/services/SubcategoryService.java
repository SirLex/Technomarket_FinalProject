package com.technomarket.model.services;

import com.technomarket.exceptions.BadRequestException;
import com.technomarket.model.dtos.subcategory.SubcategoryAddDTO;
import com.technomarket.model.dtos.subcategory.SubcategoryResponseDTO;
import com.technomarket.model.pojos.Subcategory;
import com.technomarket.model.repositories.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private CategoryService categoryService;

    public SubcategoryResponseDTO addSubcategory(SubcategoryAddDTO subcategoryDTO) {
        if (subcategoryRepository.existsByName(subcategoryDTO.getName())) {
            throw new BadRequestException("Subcategory already exists");
        }
        Subcategory subcategory = new Subcategory();
        subcategory.setName(subcategoryDTO.getName());
        subcategory.setCategory(categoryService.getWholeById(subcategoryDTO.getCategoryId()));
        subcategoryRepository.save(subcategory);
        return new SubcategoryResponseDTO(subcategory);
    }

    public SubcategoryResponseDTO getById(int id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new BadRequestException("Subcategory with this id doesn't exists");
        }
        return new SubcategoryResponseDTO(subcategoryRepository.getById(id));
    }

    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }
}
