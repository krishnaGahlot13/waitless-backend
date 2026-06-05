package com.waitless.backend.services;

import com.waitless.backend.dto.BusinessRegisterDTO;
import com.waitless.backend.dto.BusinessRegisterResponseDTO;

import com.waitless.backend.exception.DuplicateResourceException;
import com.waitless.backend.model.Businesses;
import com.waitless.backend.model.Users;
import com.waitless.backend.model.Roles;
import com.waitless.backend.repository.BusinessRepo;
import com.waitless.backend.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BusinessRegisterService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BusinessRepo businessRepo;

    public BusinessRegisterResponseDTO businessRegister(BusinessRegisterDTO dto){

        if (userRepo.existsByEmail(dto.getEmail())){
            throw new DuplicateResourceException("Email already exists");

        }
        Users userBusiness = new Users();

        userBusiness.setEmail(dto.getEmail());
        userBusiness.setPassword(passwordEncoder.encode(dto.getPassword()));
        userBusiness.setMobileNumber(dto.getMobileNumber());
        userBusiness.setRole(Roles.BUSINESS);

        userRepo.save(userBusiness);
        Businesses business = new Businesses();

        business.setBusinessName(dto.getBusinessName());
        business.setAddress(dto.getAddress());
        business.setOpeningTime(dto.getOpeningTime());
        business.setClosingTime(dto.getClosingTime());

        business.setAverageTime(15); // default value

        business.setUser(userBusiness);

        businessRepo.save(business);

        return new BusinessRegisterResponseDTO (userBusiness.getEmail(),"Business Register Successfully");
    }

}
