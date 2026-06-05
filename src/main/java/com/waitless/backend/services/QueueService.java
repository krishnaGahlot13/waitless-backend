package com.waitless.backend.services;

import com.waitless.backend.dto.queueDTO.CreateQueueDTO;
import com.waitless.backend.dto.queueDTO.QueueResponseDTO;
import com.waitless.backend.exception.ForbiddenException;
import com.waitless.backend.exception.ResourceNotFoundException;
import com.waitless.backend.model.Queue;
import com.waitless.backend.model.QueueCurrentStatus;
import com.waitless.backend.model.Services;
import com.waitless.backend.repository.QueueRepo;
import com.waitless.backend.repository.ServiceRepo;
import com.waitless.backend.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueueService {

    @Autowired
    private QueueRepo queueRepo;

    @Autowired
    private ServiceRepo servicesRepo;


    private QueueResponseDTO mapToDTO(Queue queue) {

        return new QueueResponseDTO(
                queue.getQueueId(),
                queue.getQueueName(),
                queue.getMaxCapacity(),
                queue.isActive(),
                queue.getCurrentToken(),
                queue.getServingToken(),
                queue.getQueueCode(),
                queue.getCurrentStatus(),
                queue.getServices() != null ? queue.getServices().getServicesId() : null,
                queue.getServices() != null ? queue.getServices().getServicesName() : null
        );

    }

    @Transactional
    public String createQueue(CreateQueueDTO dto) {

        Services service = servicesRepo.findById(dto.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        // FIX: verify the authenticated user owns the business that owns this service.
        // Without this, any BUSINESS user could create queues under another business.
        int currentUserId = SecurityUtils.getCurrentUserId();
        int serviceOwnerId = service.getBusinesses().getUser().getUserId();

        if (serviceOwnerId != currentUserId && !SecurityUtils.isAdmin()) {
            throw new ForbiddenException("You do not own the business for this service");
        }

        Queue queue = new Queue();
        queue.setQueueName(dto.getQueueName());
        queue.setMaxCapacity(dto.getMaxCapacity());
        queue.setQueueCode(dto.getQueueCode());
        queue.setCurrentToken(0);
        queue.setToken(0);
        queue.setCurrentStatus(QueueCurrentStatus.ACTIVE);
        queue.setServingToken(0);
        queue.setServices(service);

        Queue savedQueue = queueRepo.save(queue);

        service.setQueue(savedQueue);
        servicesRepo.save(service);

        return "Queue created";
    }

    public QueueResponseDTO getQueueById(int id) {

        Queue queue = queueRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));

        return mapToDTO(queue);
    }

    @Transactional
    public String deleteQueue(int id) {

        int currentUserId = SecurityUtils.getCurrentUserId();

        Queue queue = queueRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));

        int ownerId = queue.getServices()
                .getBusinesses()
                .getUser()
                .getUserId();

        if (ownerId != currentUserId && !SecurityUtils.isAdmin()) {
            throw new ForbiddenException("You cannot delete this queue");
        }

        queueRepo.delete(queue);

        return "Queue deleted";
    }

    public List<Queue> getMyQueues(String email) {
        return queueRepo.findAllByBusinessUserEmail(email);
    }

    public Map<String, Integer> getMyQueueStats(String email) {
        List<Queue> queues = queueRepo.findAllByBusinessUserEmail(email);
        int totalQueues = queues.size();
        int activeCustomers = queues.stream()
                .mapToInt(q -> Math.max(0, q.getCurrentToken() - q.getServingToken()))
                .sum();
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalQueues", totalQueues);
        stats.put("activeCustomers", activeCustomers);
        return stats;
    }

    public QueueResponseDTO getQueueByServiceId(int serviceId) {
        Queue queue = queueRepo.findByServicesServicesId(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("No queue found for this service"));

        return mapToDTO(queue);
    }
}