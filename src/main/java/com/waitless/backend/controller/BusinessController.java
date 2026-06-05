package com.waitless.backend.controller;

import com.waitless.backend.dto.businessDTO.AddBusinessDTO;
import com.waitless.backend.dto.businessDTO.BusinessResponseDTO;
import com.waitless.backend.services.BusinessService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/{id}")
    public ResponseEntity<BusinessResponseDTO> findBusinessById(@PathVariable int id) {
        // FIX: returns DTO instead of raw entity to avoid lazy-load exceptions
        // and prevent over-exposure of internal fields
        return ResponseEntity.ok(businessService.findBusinessById(id));
    }

    @DeleteMapping("/deleteBusiness/{id}")
    public ResponseEntity<String> deleteBusinessById(@PathVariable int id) {
        return ResponseEntity.ok(businessService.deleteBusiness(id));
    }

}