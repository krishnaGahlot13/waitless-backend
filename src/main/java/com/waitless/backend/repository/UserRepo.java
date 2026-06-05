package com.waitless.backend.repository;

import com.waitless.backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<Users,Integer> {
    @Override
    Optional<Users> findById(Integer integer);

    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);


}
