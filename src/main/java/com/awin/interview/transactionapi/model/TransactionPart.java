package com.awin.interview.transactionapi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="transaction_parts")
public class TransactionPart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="transaction_id", nullable = false)
    private Transaction transaction;

    @Column(nullable = false, scale = 2)
    private BigDecimal saleAmount;

    @Column(nullable = false, scale = 2)
    private BigDecimal commissionAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void preUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }


    protected TransactionPart() {
    }

    public TransactionPart(BigDecimal saleAmount, BigDecimal commissionAmount) {
        this.saleAmount = saleAmount;
        this.commissionAmount = commissionAmount;
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
