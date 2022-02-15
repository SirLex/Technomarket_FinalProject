package com.technomarket.model.repositories;

import com.technomarket.model.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);
    User findByEmail(String email);
    List<User> findAllBySubscribed(boolean subscribed);
}
