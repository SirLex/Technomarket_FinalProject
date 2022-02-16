package com.technomarket.model.dtos;


import com.technomarket.model.pojos.Attributes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithAllDTO {


    private ProductDTO product;

    private List<Attributes> attributes;

    private List<ReviewDTO> reviews;


}
