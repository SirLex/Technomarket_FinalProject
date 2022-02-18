package com.technomarket.model.repositories;


import com.technomarket.model.pojos.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByName(String name);

    @Modifying
    @Query(value ="SELECT p.* FROM products AS p JOIN orders_have_products AS ohp ON p.id=ohp.product_id WHERE ohp.order_id = ?", nativeQuery = true)
    List<Product> findAllProductsInOrder(int id);
}
