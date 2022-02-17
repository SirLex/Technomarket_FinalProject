package com.technomarket.model.repositories;

import com.technomarket.model.pojos.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    boolean existsByName(String name);
}
