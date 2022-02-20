package com.technomarket.model.repositories;

import com.technomarket.model.pojos.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationToken, Integer> {
    VerificationToken getByToken(String token);
}
