package com.technomarket.model.services;

import com.technomarket.exceptions.BadRequestException;
import com.technomarket.model.dtos.category.CategoryResponseDTO;
import com.technomarket.model.dtos.subcategory.SubcategoryAddDTO;
import com.technomarket.model.dtos.subcategory.SubcategoryResponseDTO;
import com.technomarket.model.pojos.Subcategory;
import com.technomarket.model.repositories.CategoryRepository;
import com.technomarket.model.repositories.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttributeService attributeService;

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

    public Subcategory getWholeById(int id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new BadRequestException("Subcategory with this id doesn't exists");
        }
        return subcategoryRepository.getById(id);
    }

    public SubcategoryResponseDTO addAttributeToAllowed(int subId, int attId) {
        Subcategory subcategory = getWholeById(subId);
        subcategory.getAllowedAttributes().add(attributeService.getById(attId));
        subcategoryRepository.save(subcategory);
        return new SubcategoryResponseDTO(subcategory);
    }

    public List<CategoryResponseDTO> getAllCategoriesWithSubCategories() {
        return categoryRepository.findAll().stream().map(cat -> new CategoryResponseDTO(cat)).toList();
    }
}
