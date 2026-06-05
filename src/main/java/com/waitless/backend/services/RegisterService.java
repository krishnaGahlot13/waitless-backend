package com.waitless.backend.services;

import com.waitless.backend.dto.RegisterDTO;
import com.waitless.backend.dto.RegisterResponseDTO;
import com.waitless.backend.exception.DuplicateResourceException;
import com.waitless.backend.model.Users;
import com.waitless.backend.model.Roles;
import com.waitless.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    public RegisterResponseDTO registerService(RegisterDTO registerDTO) {

        if (userRepo.existsByEmail(registerDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        Users users = new Users();
        users.setEmail(registerDTO.getEmail());
        users.setMobileNumber(registerDTO.getMobileNumber());
        users.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        users.setRole(Roles.USER);

        userRepo.save(users);

        return new RegisterResponseDTO(registerDTO.getEmail(), "Registered successfully");
    }
}