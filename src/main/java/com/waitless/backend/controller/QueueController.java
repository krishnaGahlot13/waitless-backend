package com.waitless.backend.controller;


import com.waitless.backend.dto.queueDTO.CreateQueueDTO;
import com.waitless.backend.dto.queueDTO.QueueResponseDTO;
import com.waitless.backend.model.Queue;
import com.waitless.backend.services.QueueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/queues")
public class QueueController {
    @Autowired
    private QueueService queueService;

    @PostMapping("/createQueue")
    public String createQueue(@Valid @RequestBody CreateQueueDTO dto){
        return queueService.createQueue(dto);
    }

    @GetMapping("{id}")
    public QueueResponseDTO getQueueById(@PathVariable int id){
        return queueService.getQueueById(id);
    }
    @DeleteMapping("{id}")
    public String deleteQueue(@PathVariable int id){
        return queueService.deleteQueue(id);

    }
    @GetMapping("/byService/{serviceId}")
    public QueueResponseDTO getQueueByServiceId(@PathVariable int serviceId) {
        return queueService.getQueueByServiceId(serviceId);
    }

    @GetMapping("/myQueues")
    public ResponseEntity<List<Queue>> getMyQueues(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(queueService.getMyQueues(userDetails.getUsername()));
    }
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Integer>> getMyQueueStats(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(queueService.getMyQueueStats(userDetails.getUsername()));
    }
}
