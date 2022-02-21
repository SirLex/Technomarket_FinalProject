package com.technomarket.model.dtos.product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductKeywordsDTO {

    @NotNull
    List<String> keywords;
}
