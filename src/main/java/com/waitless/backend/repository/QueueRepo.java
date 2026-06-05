package com.waitless.backend.repository;

import com.waitless.backend.model.Queue;
import com.waitless.backend.model.QueueCurrentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueRepo extends JpaRepository<Queue,Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)

    @Query("SELECT q FROM Queue q WHERE q.queueId = :id")
    Optional<Queue> findByIdForUpdate(@Param("id") int id);

    Optional<Queue> findByServicesServicesIdAndCurrentStatus(
            Integer servicesId,
            QueueCurrentStatus status
    );
    Optional<Queue> findByServicesServicesId(int servicesId);

    @Query("SELECT q FROM Queue q WHERE q.services.businesses.user.email = :email")
    List<Queue> findAllByBusinessUserEmail(@Param("email") String email);
}
