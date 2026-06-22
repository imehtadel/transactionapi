package com.awin.interview.transactionapi.repository;

import com.awin.interview.transactionapi.model.OutboxEvent;
import com.awin.interview.transactionapi.model.OutboxEventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
    List<OutboxEvent> findTop50ByStatusOrderByCreatedAtAsc(OutboxEventStatus status);
}
