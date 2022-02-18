package com.technomarket.model.repositories;

import com.technomarket.model.relationentity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
}
