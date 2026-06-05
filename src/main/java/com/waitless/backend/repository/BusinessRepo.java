package com.waitless.backend.repository;

import com.waitless.backend.model.Businesses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepo extends JpaRepository<Businesses,Integer> {
    Optional<Businesses> findByUser_UserId(int userId);

    Businesses findFirstByUser_UserId(int userId);
}
