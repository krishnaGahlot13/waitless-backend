package com.waitless.backend.controller;

import com.waitless.backend.dto.businessDTO.BusinessResponseDTO;
import com.waitless.backend.dto.servicesDTO.ServiceResponseDTO;
import com.waitless.backend.services.BusinessService;
import com.waitless.backend.services.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AllObjectController {

    @Autowired
    private BusinessService businessService;
    @Autowired
    private ServicesService servicesService;



    @GetMapping("/getAllBusiness")
    public List<BusinessResponseDTO> getAllBusiness(){
        return businessService.getAllBusiness();
    }
    @GetMapping("/allServices")
    public ResponseEntity<List<ServiceResponseDTO>> getAllServices() {
        return ResponseEntity.ok(servicesService.getService());
    }



}
