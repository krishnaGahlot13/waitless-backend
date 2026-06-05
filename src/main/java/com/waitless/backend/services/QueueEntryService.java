package com.waitless.backend.services;

import com.waitless.backend.dto.queueDTO.CurrentStatusQueueDTO;
import com.waitless.backend.dto.queueDTO.QueueEntryDTO;
import com.waitless.backend.dto.queueDTO.QueueEntryResponseDTO;
import com.waitless.backend.exception.BadRequestException;
import com.waitless.backend.exception.ForbiddenException;
import com.waitless.backend.exception.QueueFullException;
import com.waitless.backend.exception.ResourceNotFoundException;
import com.waitless.backend.model.*;
import com.waitless.backend.repository.QueueEntryRepo;
import com.waitless.backend.repository.QueueRepo;
import com.waitless.backend.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class QueueEntryService {

    @Autowired
    private QueueEntryRepo queueEntryRepo;

    @Autowired
    private QueueRepo queueRepo;

    @Transactional
    public QueueEntryResponseDTO createQueueEntry(QueueEntryDTO dto) {

        Queue queue = queueRepo
                .findByServicesServicesIdAndCurrentStatus(
                        dto.getServicesId(),
                        QueueCurrentStatus.ACTIVE
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException("Queue not found"));




        if (queue.getCurrentToken() >= queue.getMaxCapacity()) {
            throw new QueueFullException("Queue is full");
        }

        QueueEntry queueEntry = new QueueEntry();
        queueEntry.setUser(SecurityUtils.getCurrentUser());
        queueEntry.setCustomerName(dto.getCustomerName());
        queueEntry.setMobileNumber(dto.getMobileNumber());
        queueEntry.setQueue(queue);
        queueEntry.setStatus(QueueStatus.WAITING);
        queueEntry.setJoinedAt(LocalDateTime.now());

        int nextToken = queue.getCurrentToken() + 1;

        queueEntry.setTokenNumber(nextToken);
        queueEntry.setDisplayToken(queue.getQueueCode() + "-" + nextToken);

        queue.setCurrentToken(nextToken);

        queueRepo.save(queue);

        QueueEntry savedEntry = queueEntryRepo.save(queueEntry);

        QueueEntryResponseDTO responseDTO = new QueueEntryResponseDTO();

        responseDTO.setCustomerName(savedEntry.getCustomerName());
        responseDTO.setDisplayToken(savedEntry.getDisplayToken());
        responseDTO.setStatus(savedEntry.getStatus());

        return responseDTO;
    }

    public CurrentStatusQueueDTO currentStatus(String displayToken) {

        QueueEntry queueEntry = queueEntryRepo.findByDisplayToken(displayToken)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        Queue queue = queueEntry.getQueue();

        Services services = queue.getServices();

        int myToken = queueEntry.getTokenNumber();

        long waitingAhead =
                queueEntryRepo.countByQueueAndStatusAndTokenNumberLessThan(
                        queue,
                        QueueStatus.WAITING,
                        myToken
                );

        boolean hasActiveCustomer = queueEntryRepo
                .findFirstByQueueAndStatus(queue, QueueStatus.ACTIVE)
                .isPresent();

        int estimatedWait =
                services.getEstimatedTime()
                        * (int) (waitingAhead + (hasActiveCustomer ? 1 : 0));

        CurrentStatusQueueDTO dto = new CurrentStatusQueueDTO();

        dto.setDisplayToken(queueEntry.getDisplayToken());
        dto.setWaitingCount((int) waitingAhead);
        dto.setEstimatedWait(estimatedWait);

        return dto;
    }


    @Transactional
    public String nextCustomer(int queueId) {

        Queue queue = queueRepo.findByIdForUpdate(queueId)
                .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));

        Optional<QueueEntry> activeOpt =
                queueEntryRepo.findFirstByQueueAndStatus(
                        queue,
                        QueueStatus.ACTIVE
                );

        if (activeOpt.isPresent()) {

            QueueEntry activeEntry = activeOpt.get();

            activeEntry.setStatus(QueueStatus.COMPLETED);

            queueEntryRepo.save(activeEntry);
        }

        activateNextWaiting(queue);

        return "Next customer activated";
    }

    @Transactional
    public String cancelled(int queueId, int tokenNumber) {

        Queue queue = queueRepo.findByIdForUpdate(queueId)
                .orElseThrow(() -> new ResourceNotFoundException("Queue not found"));

        Optional<QueueEntry> cancelledEntry =
                queueEntryRepo.findByQueueAndTokenNumber(
                        queue,
                        tokenNumber
                );

        if (cancelledEntry.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Token not found in this queue"
            );
        }

        QueueEntry entry = cancelledEntry.get();

        if (entry.getStatus() == QueueStatus.COMPLETED) {
            throw new BadRequestException(
                    "Customer has already been served"
            );
        }

        if (entry.getStatus() == QueueStatus.CANCELED) {
            throw new BadRequestException(
                    "Entry is already cancelled"
            );
        }

        int currentUserId = SecurityUtils.getCurrentUserId();

        boolean isAdmin = SecurityUtils.isAdmin();

        boolean isBusiness =
                SecurityUtils.getCurrentUser()
                        .getRole()
                        .name()
                        .equals("BUSINESS");

        if (!isAdmin) {

            if (isBusiness) {

                int queueOwnerId =
                        queue.getServices()
                                .getBusinesses()
                                .getUser()
                                .getUserId();

                if (queueOwnerId != currentUserId) {
                    throw new ForbiddenException(
                            "You do not own this queue"
                    );
                }

            } else {

                if (entry.getUser() == null) {
                    throw new ForbiddenException(
                            "Queue entry has no associated user"
                    );
                }

                int entryOwnerId =
                        entry.getUser().getUserId();

                if (entryOwnerId != currentUserId) {
                    throw new ForbiddenException(
                            "You can cancel only your own queue entry"
                    );
                }
            }
        }
        boolean wasActive =
                entry.getStatus() == QueueStatus.ACTIVE;

        entry.setStatus(QueueStatus.CANCELED);

        queueEntryRepo.save(entry);

        if (wasActive) {
            activateNextWaiting(queue);
        }

        return "Entry cancelled";
    }

    private void activateNextWaiting(Queue queue) {

        int servingToken = queue.getServingToken();

        boolean found = false;

        while (servingToken < queue.getCurrentToken()) {

            int nextToken = ++servingToken;

            Optional<QueueEntry> entryOpt =
                    queueEntryRepo.findByQueueAndTokenNumber(
                            queue,
                            nextToken
                    );

            if (entryOpt.isEmpty()) {

                queue.setServingToken(nextToken);

                continue;
            }

            QueueEntry entry = entryOpt.get();

            if (entry.getStatus() == QueueStatus.CANCELED) {

                queue.setServingToken(nextToken);

                continue;
            }

            if (entry.getStatus() == QueueStatus.WAITING) {

                entry.setStatus(QueueStatus.ACTIVE);

                queue.setServingToken(nextToken);

                queueEntryRepo.save(entry);

                queueRepo.save(queue);

                found = true;

                break;
            }
        }

        // NEW FIX
        if (!found) {

            queue.setServingToken(queue.getCurrentToken());

            queueRepo.save(queue);
        }
    }
}