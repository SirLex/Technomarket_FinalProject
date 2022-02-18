package com.technomarket.model.repositories;


import com.technomarket.model.pojos.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    boolean existsByName(String name);

    //List<Product> findUserFavouriteProducts(int id);
}
