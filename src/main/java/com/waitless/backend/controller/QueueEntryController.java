package com.waitless.backend.controller;


import com.waitless.backend.dto.queueDTO.CurrentStatusQueueDTO;
import com.waitless.backend.dto.queueDTO.QueueEntryDTO;
import com.waitless.backend.dto.queueDTO.QueueEntryResponseDTO;
import com.waitless.backend.services.QueueEntryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue-entries")
public class QueueEntryController {
    @Autowired
    private QueueEntryService queueEntryService;

    @PostMapping("/join")
    public ResponseEntity<QueueEntryResponseDTO> joinQueue(@RequestBody QueueEntryDTO dto){
        return ResponseEntity.ok(queueEntryService.createQueueEntry(dto));
    }

    @GetMapping("/status/{displayToken}")
    public ResponseEntity<CurrentStatusQueueDTO> currentStatus(@PathVariable String displayToken) {
        return ResponseEntity.ok(queueEntryService.currentStatus(displayToken));
    }

    @PostMapping("/{queueId}/next")
    public ResponseEntity<String> nextCustomer (@PathVariable int queueId){
        return ResponseEntity.ok(queueEntryService.nextCustomer(queueId));
    }

    @DeleteMapping("/{queueId}/{TokenNumber}")
    public ResponseEntity<String> cancelEntry (@PathVariable int queueId, @PathVariable int TokenNumber){
        return ResponseEntity.ok(queueEntryService.cancelled(queueId,TokenNumber));
    }


}
