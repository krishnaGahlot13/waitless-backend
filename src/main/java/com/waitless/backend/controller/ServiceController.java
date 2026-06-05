package com.waitless.backend.controller;

import com.waitless.backend.dto.servicesDTO.CreateServiceDTO;
import com.waitless.backend.dto.servicesDTO.ServiceResponseDTO;
import com.waitless.backend.model.Services;
import com.waitless.backend.services.ServicesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServicesService servicesService;

    @PostMapping("/addService")
    public ResponseEntity<String> createService(@Valid @RequestBody CreateServiceDTO dto) {
        System.out.println("CONTROLLER HIT");

        System.out.println("DTO = " + dto.getServicesName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicesService.createService(dto));
    }

    // FIX: returns ServiceResponseDTO instead of raw Services entity
    // to avoid lazy-load exceptions and over-exposure of internal data
    @GetMapping("/ServiceOf/{id}")
    public ResponseEntity<ServiceResponseDTO> findServiceById(@PathVariable int id) throws Exception {
        return ResponseEntity.ok(servicesService.findServiceById(id));
    }

    @DeleteMapping("/deleteService/{id}")
    public ResponseEntity<String> deleteServiceById(@PathVariable int id) {
        return ResponseEntity.ok(servicesService.deleteService(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateService(
            @PathVariable int id,
            @Valid @RequestBody CreateServiceDTO dto) {
        return ResponseEntity.ok(servicesService.updateService(id, dto));
    }

    @GetMapping("/myServices")
    public List<ServiceResponseDTO> myServices() throws Exception {
        return servicesService.getMyServices();
    }
}