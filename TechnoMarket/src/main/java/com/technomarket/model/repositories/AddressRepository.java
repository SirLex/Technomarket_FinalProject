package com.technomarket.model.repositories;

import com.technomarket.model.pojos.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUserId(Long userId);

    Address findAddressById(Long addressId);

    void deleteAddressById(Long addressId);
}