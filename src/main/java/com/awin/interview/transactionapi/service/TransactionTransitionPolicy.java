package com.awin.interview.transactionapi.service;

import com.awin.interview.transactionapi.model.TransactionStatus;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Component
public class TransactionTransitionPolicy {
    private static final Map<TransactionStatus, Set<TransactionStatus>> ALLOWED_TRANSITIONS = new EnumMap<>(TransactionStatus.class);
    static {
        ALLOWED_TRANSITIONS.put(TransactionStatus.PENDING, EnumSet.of(TransactionStatus.APPROVED, TransactionStatus.DECLINED));
        ALLOWED_TRANSITIONS.put(TransactionStatus.APPROVED, EnumSet.noneOf(TransactionStatus.class));
        ALLOWED_TRANSITIONS.put(TransactionStatus.DECLINED, EnumSet.noneOf(TransactionStatus.class));
    }

    public void validateTransition(TransactionStatus from, TransactionStatus to){
        if(!ALLOWED_TRANSITIONS.getOrDefault(from, EnumSet.noneOf(TransactionStatus.class)).contains(to)){
            throw new IllegalStateException("Invalid transaction status transition from " + from + " to " + to );
        }
    }
}
