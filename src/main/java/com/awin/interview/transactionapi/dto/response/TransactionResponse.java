package com.awin.interview.transactionapi.dto.response;

import com.awin.interview.transactionapi.model.TransactionStatus;

import java.util.UUID;

public record TransactionResponse(
        UUID id,
        TransactionStatus status
) {
}
