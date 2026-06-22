package com.awin.interview.transactionapi.service;

import com.awin.interview.transactionapi.dto.request.CreateTransactionPartRequest;
import com.awin.interview.transactionapi.dto.request.CreateTransactionRequest;
import com.awin.interview.transactionapi.dto.response.TransactionResponse;
import com.awin.interview.transactionapi.exception.TransactionNotFoundException;
import com.awin.interview.transactionapi.model.Transaction;
import com.awin.interview.transactionapi.model.TransactionPart;
import com.awin.interview.transactionapi.model.TransactionStatus;
import com.awin.interview.transactionapi.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionTransitionPolicy transactionTransitionPolicy;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionTransitionPolicy transactionTransitionPolicy) {
        this.transactionRepository = transactionRepository;
        this.transactionTransitionPolicy = transactionTransitionPolicy;
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

        transactionTransitionPolicy.validateTransition(transaction.getStatus(), TransactionStatus.APPROVED);
        transaction.updateStatus(TransactionStatus.APPROVED);
        return new TransactionResponse(transaction.getId(), transaction.getStatus());
    }

    @Override
    @Transactional
    public TransactionResponse declineTransaction(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow( () -> new TransactionNotFoundException("Transaction not found: " + id));

        transactionTransitionPolicy.validateTransition(transaction.getStatus(), TransactionStatus.DECLINED);
        transaction.updateStatus(TransactionStatus.DECLINED);
        return new TransactionResponse(transaction.getId(), transaction.getStatus());
    }
}
