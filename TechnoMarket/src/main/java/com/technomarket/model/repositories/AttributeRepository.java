package com.technomarket.model.repositories;

import com.technomarket.model.pojos.Attributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttributeRepository extends JpaRepository<Attributes, Integer> {

    boolean existsByName(String name);
}
