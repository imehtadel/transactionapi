package com.awin.interview.transactionapi.service;

import com.awin.interview.transactionapi.dto.event.TransactionApprovedEvent;
import com.awin.interview.transactionapi.dto.request.CreateTransactionPartRequest;
import com.awin.interview.transactionapi.dto.request.CreateTransactionRequest;
import com.awin.interview.transactionapi.dto.response.TransactionResponse;
import com.awin.interview.transactionapi.exception.TransactionNotFoundException;
import com.awin.interview.transactionapi.model.OutboxEvent;
import com.awin.interview.transactionapi.model.Transaction;
import com.awin.interview.transactionapi.model.TransactionPart;
import com.awin.interview.transactionapi.model.TransactionStatus;
import com.awin.interview.transactionapi.repository.OutboxEventRepository;
import com.awin.interview.transactionapi.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    public static final String TRANSACTION_APPROVED = "TRANSACTION_APPROVED";
    private static final String TRANSACTION_DECLINED = "TRANSACTION_DECLINED";
    private final TransactionRepository transactionRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final TransactionTransitionValidator transactionTransitionValidator;
    private final ObjectMapper objectMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, OutboxEventRepository outboxEventRepository,
                                  TransactionTransitionValidator transactionTransitionValidator, ObjectMapper objectMapper) {
        this.transactionRepository = transactionRepository;
        this.outboxEventRepository = outboxEventRepository;
        this.transactionTransitionValidator = transactionTransitionValidator;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        Transaction transaction = new Transaction(request.saleAmount(), request.commissionAmount());
        for(CreateTransactionPartRequest part : request.parts()){
            transaction.addPart(new TransactionPart(part.saleAmount(), part.commissionAmount()));
        }
        Transaction savedTransaction =  transactionRepository.save(transaction);
        return new TransactionResponse(savedTransaction.getId(), savedTransaction.getStatus());

    }

    @Override
    @Transactional
    public TransactionResponse approveTransaction(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow( () -> new TransactionNotFoundException("Transaction not found: " + id));

        transactionTransitionValidator.validateTransition(transaction.getStatus(), TransactionStatus.APPROVED);
        transaction.updateStatus(TransactionStatus.APPROVED);

        OutboxEvent outboxEvent = OutboxEvent.pending("Transaction", transaction.getId().toString(),
                TRANSACTION_APPROVED, createOutboxEventPayload(transaction));
        outboxEventRepository.save(outboxEvent);

        return new TransactionResponse(transaction.getId(), transaction.getStatus());
    }

    @Override
    @Transactional
    public TransactionResponse declineTransaction(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow( () -> new TransactionNotFoundException("Transaction not found: " + id));

        transactionTransitionValidator.validateTransition(transaction.getStatus(), TransactionStatus.DECLINED);
        transaction.updateStatus(TransactionStatus.DECLINED);
        OutboxEvent outboxEvent = OutboxEvent.pending("Transaction", transaction.getId().toString(),
                TRANSACTION_DECLINED, createOutboxEventPayload(transaction));
        outboxEventRepository.save(outboxEvent);
        return new TransactionResponse(transaction.getId(), transaction.getStatus());
    }

    private String createOutboxEventPayload(Transaction transaction){
        return objectMapper.writeValueAsString(new TransactionApprovedEvent(transaction.getId(),
                transaction.getStatus().name(), LocalDateTime.now()));
    }
}
