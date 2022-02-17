package com.technomarket.model.dtos.subcategory;

import com.technomarket.model.dtos.category.CategoryResponseDTO;
import com.technomarket.model.dtos.category.CategoryResponseWithoutSubDTO;
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
public class SubcategoryResponseDTO {

    @NotNull
    private int id;
    @NotBlank
    private String name;
    @NotNull
    private CategoryResponseWithoutSubDTO category;

    public SubcategoryResponseDTO(Subcategory subcategory){
        this.id = subcategory.getId();
        this.name = subcategory.getName();
        this.category = new CategoryResponseWithoutSubDTO(subcategory.getCategory());
    }

}