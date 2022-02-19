package com.technomarket.model.dtos.category;

import com.technomarket.model.dtos.subcategory.SubcategoryResponseWithoutCategoryDTO;
import com.technomarket.model.pojos.Category;
import com.technomarket.model.pojos.Subcategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
@Setter
@Getter
@NoArgsConstructor
public class CategoryResponseDTO {

    @NotNull
    private int id;
    @NotBlank
    private String name;

    private List<SubcategoryResponseWithoutCategoryDTO> subcategories;

    public CategoryResponseDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.subcategories = new ArrayList<>();
        if (category.getSubcategories() != null) {
            for (Subcategory subcategory : category.getSubcategories()) {
                subcategories.add(new SubcategoryResponseWithoutCategoryDTO(subcategory));
            }
        }
    }

}