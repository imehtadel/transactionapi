package com.awin.interview.transactionapi.service;

import com.awin.interview.transactionapi.dto.request.CreateTransactionRequest;
import com.awin.interview.transactionapi.dto.response.TransactionResponse;

import java.util.UUID;


public interface TransactionService {
    TransactionResponse createTransaction(CreateTransactionRequest createTransactionRequest);

    TransactionResponse approveTransaction(UUID id);

    TransactionResponse declineTransaction(UUID id);
}
