package com.awin.interview.transactionapi.controller;

import com.awin.interview.transactionapi.dto.request.CreateTransactionRequest;
import com.awin.interview.transactionapi.dto.response.TransactionResponse;
import com.awin.interview.transactionapi.exception.InvalidRequestException;
import com.awin.interview.transactionapi.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest createTransactionRequest) {
        if(createTransactionRequest == null){
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "Request body should not be null");
        }
        TransactionResponse response = transactionService.createTransaction(createTransactionRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/approve")
    public ResponseEntity<TransactionResponse> approve(@PathVariable UUID id){
        TransactionResponse response = transactionService.approveTransaction(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/decline")
    public ResponseEntity<TransactionResponse> decline(@PathVariable UUID id){
        TransactionResponse response = transactionService.declineTransaction(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
