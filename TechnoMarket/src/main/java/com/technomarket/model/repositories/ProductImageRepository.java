package com.technomarket.model.repositories;

import com.technomarket.model.pojos.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductImageRepository  extends JpaRepository<ProductImage,Integer> {



}
