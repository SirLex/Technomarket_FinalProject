package com.technomarket.model.repositories;

import com.technomarket.model.compositekeys.OrderProductKey;
import com.technomarket.model.relationentity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductKey> {

    List<OrderProduct> findAllByOrder_Id(int id);
}
