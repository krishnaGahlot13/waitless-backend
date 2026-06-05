package com.waitless.backend.repository;

import com.waitless.backend.model.Queue;
import com.waitless.backend.model.QueueEntry;
import com.waitless.backend.model.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueEntryRepo extends JpaRepository<QueueEntry, Integer> {

    Optional<QueueEntry> findByQueueAndTokenNumber(
            Queue queue,
            int tokenNumber
    );

    Optional<QueueEntry> findFirstByQueueAndStatus(
            Queue queue,
            QueueStatus queueStatus
    );

    long countByQueueAndStatusAndTokenNumberLessThan(
            Queue queue,
            QueueStatus status,
            int tokenNumber
    );

    Optional <QueueEntry> findByDisplayToken(String displayToken);
}