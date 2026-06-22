package com.awin.interview.transactionapi.dto.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionApprovedEvent (
    UUID transactionId,
    String status,
    LocalDateTime occurredAt){

}
