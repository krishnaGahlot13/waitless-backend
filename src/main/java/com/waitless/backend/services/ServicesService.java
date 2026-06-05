package com.waitless.backend.services;

import com.waitless.backend.dto.servicesDTO.CreateServiceDTO;
import com.waitless.backend.dto.servicesDTO.ServiceResponseDTO;
import com.waitless.backend.exception.ForbiddenException;
import com.waitless.backend.exception.ResourceNotFoundException;
import com.waitless.backend.model.Businesses;
import com.waitless.backend.model.Queue;
import com.waitless.backend.model.Services;
import com.waitless.backend.repository.BusinessRepo;
import com.waitless.backend.repository.QueueRepo;
import com.waitless.backend.repository.ServiceRepo;
import com.waitless.backend.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicesService {

    @Autowired
    private ServiceRepo servicesRepo;
    @Autowired
    private QueueRepo queueRepo;
    @Autowired
    private BusinessRepo businessRepo;

    @Transactional
    public String createService(CreateServiceDTO dto) {
        try {

            if (!SecurityUtils.isBusiness() && !SecurityUtils.isAdmin()) {
                throw new ForbiddenException("Only business users can create services");
            }

            int currentUserId = SecurityUtils.getCurrentUserId();


            Businesses business = businessRepo.findFirstByUser_UserId(currentUserId);

            System.out.println("Business Found = " + (business != null));

            Services services = new Services();
            services.setServicesName(dto.getServicesName());
            services.setPrice(dto.getPrice());
            services.setEstimatedTime(dto.getEstimatedTime());
            services.setBusinesses(business);

            servicesRepo.save(services);
            return "Service created";

        } catch (Exception e) {
            System.out.println("ERROR IN CREATE SERVICE = " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // FIX: returns DTOs instead of raw entities to avoid lazy-load exceptions
    public List<ServiceResponseDTO> getService() {
        return servicesRepo.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public String deleteService(int id) {
        Services services = servicesRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        int currentUserId = SecurityUtils.getCurrentUserId();
        int ownerId = services.getBusinesses().getUser().getUserId();

        if (ownerId != currentUserId && !SecurityUtils.isAdmin()) {
            throw new ForbiddenException("You cannot modify this service");
        }

        // Null out the queue's reference to this service before deleting
        Queue queue = services.getQueue();
        if (queue != null) {
            queue.setServices(null);
            queueRepo.save(queue);
        }

        servicesRepo.delete(services);
        return "Service deleted successfully";
    }

    // FIX: returns DTO, removed unnecessary checked "throws Exception"
    public ServiceResponseDTO findServiceById(int id) {
        Services services = servicesRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        return toDTO(services);
    }

    @Transactional
    public String updateService(int id, CreateServiceDTO dto) {
        Services services = servicesRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        int currentUserId = SecurityUtils.getCurrentUserId();
        int ownerId = services.getBusinesses().getUser().getUserId();

        if (ownerId != currentUserId && !SecurityUtils.isAdmin()) {
            throw new ForbiddenException("You cannot modify this service");
        }

        services.setServicesName(dto.getServicesName());
        services.setPrice(dto.getPrice());
        services.setEstimatedTime(dto.getEstimatedTime());

        servicesRepo.save(services);
        return "Service updated successfully";
    }

    public List<ServiceResponseDTO> getMyServices() {
        int currentUserId = SecurityUtils.getCurrentUserId();
        Businesses business = businessRepo.findFirstByUser_UserId(currentUserId);

        return servicesRepo.findByBusinesses(business)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private ServiceResponseDTO toDTO(Services s) {
        ServiceResponseDTO dto = new ServiceResponseDTO();
        dto.setServiceId(s.getServicesId());
        dto.setServicesName(s.getServicesName());
        dto.setPrice(s.getPrice());
        dto.setEstimatedTime(s.getEstimatedTime());
        dto.setBusinessId(s.getBusinesses().getBusinessId());
        dto.setBusinessName(s.getBusinesses().getBusinessName());
        return dto;
    }
}