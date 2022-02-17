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
public class CategoryResponseWithoutSubDTO {

    @NotNull
    private int id;
    @NotBlank
    private String name;

    public CategoryResponseWithoutSubDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }

}