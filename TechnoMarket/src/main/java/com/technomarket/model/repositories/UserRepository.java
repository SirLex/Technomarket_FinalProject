package com.technomarket.model.repositories;

import com.technomarket.model.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
<<<<<<< HEAD
public interface UserRepository extends JpaRepository<User, Integer> {
=======
public interface UserRepository extends JpaRepository<User, Integer> {
>>>>>>> efebfe6ac13aa2ed33888ef2dea8a39a36d3b1f9

    boolean existsByEmail(String email);
    User findByEmail(String email);
}
