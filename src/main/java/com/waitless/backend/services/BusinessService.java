package com.waitless.backend.services;

import com.waitless.backend.dto.businessDTO.AddBusinessDTO;
import com.waitless.backend.dto.businessDTO.BusinessResponseDTO;
import com.waitless.backend.exception.ForbiddenException;
import com.waitless.backend.exception.ResourceNotFoundException;
import com.waitless.backend.model.Businesses;
import com.waitless.backend.repository.BusinessRepo;
import com.waitless.backend.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepo businessRepo;

    @Transactional
    public BusinessResponseDTO addBusiness(AddBusinessDTO dto) {
        Businesses business = new Businesses();
        business.setBusinessName(dto.getBusinessName());
        business.setAddress(dto.getAddress());
        business.setOpeningTime(dto.getOpeningTime());
        business.setClosingTime(dto.getClosingTime());
        business.setAverageTime(dto.getAverageTime());
        business.setUser(SecurityUtils.getCurrentUser());

        Businesses saved = businessRepo.save(business);
        return toDTO(saved);
    }

    @Transactional
    public String deleteBusiness(int id) {
        Businesses business = businessRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));

        int currentUserId = SecurityUtils.getCurrentUserId();

        if (business.getUser().getUserId() != currentUserId && !SecurityUtils.isAdmin()) {
            throw new ForbiddenException("You cannot delete this business");
        }

        businessRepo.delete(business);
        return "Business deleted successfully";
    }

    public BusinessResponseDTO findBusinessById(int id) {
        Businesses business = businessRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found"));
        return toDTO(business);
    }

    private BusinessResponseDTO toDTO(Businesses b) {
        BusinessResponseDTO dto = new BusinessResponseDTO();
        dto.setBusinessId(b.getBusinessId());
        dto.setBusinessName(b.getBusinessName());
        dto.setAddress(b.getAddress());
        dto.setOpeningTime(b.getOpeningTime());
        dto.setClosingTime(b.getClosingTime());
        dto.setAverageTime(b.getAverageTime());
        return dto;
    }

    public List<BusinessResponseDTO> getAllBusiness(){
        return businessRepo.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }
}