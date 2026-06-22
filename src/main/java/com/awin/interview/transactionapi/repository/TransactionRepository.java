package com.awin.interview.transactionapi.repository;

import com.awin.interview.transactionapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
