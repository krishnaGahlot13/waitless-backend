package com.waitless.backend.repository;

import com.waitless.backend.model.Businesses;
import com.waitless.backend.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface ServiceRepo extends JpaRepository<Services, Integer> {
    List<Services> findByBusinesses(Businesses business);
}
