package com.technomarket.model.dtos.category;

import com.technomarket.model.pojos.Category;
import com.technomarket.model.pojos.Subcategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    List<Subcategory> subCategories;

    public CategoryResponseDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.subCategories = category.getSubcategories();
    }

}