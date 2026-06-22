package com.awin.interview.transactionapi.service;

import com.awin.interview.transactionapi.dto.request.CreateTransactionPartRequest;
import com.awin.interview.transactionapi.dto.request.CreateTransactionRequest;
import com.awin.interview.transactionapi.model.Transaction;
import com.awin.interview.transactionapi.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ExtendWith((MockitoExtension.class))
public class TransactionServiceImplTest {


    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void createTransaction_shouldSaveTransactionSuccessfully() {
        UUID transactionId = UUID.randomUUID();

//        CreateTransactionRequest request = new CreateTransactionRequest(
//                new BigDecimal("100.00"),
//                new BigDecimal("10.00"),
//                List.of(new CreateTransactionPartRequest(new BigDecimal("60.00"), new BigDecimal("6.00")),
//                        new CreateTransactionPartRequest(new BigDecimal("40.00"), new BigDecimal("4.00"))
//                ));
//        Transaction savedEntity = new Transaction();

//
//        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class)))
//                .thenReturn(savedEntity);
    }

}

