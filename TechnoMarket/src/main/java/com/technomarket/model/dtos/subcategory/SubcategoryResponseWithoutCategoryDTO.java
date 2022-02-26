package com.technomarket.model.dtos.subcategory;

import com.technomarket.model.pojos.Subcategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@Setter
@Getter
@NoArgsConstructor
public class SubcategoryResponseWithoutCategoryDTO {

    @NotNull
    private int id;
    @NotBlank
    private String name;

    public SubcategoryResponseWithoutCategoryDTO(Subcategory subcategory) {
        this.id = subcategory.getId();
        this.name = subcategory.getName();
    }

}