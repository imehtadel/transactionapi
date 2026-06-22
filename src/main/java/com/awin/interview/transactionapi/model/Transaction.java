package com.awin.interview.transactionapi.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(nullable = false, scale = 2)
    private BigDecimal saleAmount;

    @Column(nullable = false, scale = 2)
    private BigDecimal commissionAmount;

    @Version
    private Long version;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionPart> parts = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // New field locked against updates

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();;
    }

    public Transaction() {
    }

    public Transaction(BigDecimal saleAmount, BigDecimal commissionAmount) {
        this.status = TransactionStatus.PENDING;
        this.saleAmount = saleAmount;
        this.commissionAmount = commissionAmount;
    }

    public UUID getId() {
        return id;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void addPart(TransactionPart part) {
        parts.add(part);
        part.setTransaction(this);
    }

    public List<TransactionPart> getParts(){
        return parts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void updateStatus(TransactionStatus updateStatus){
        this.status= updateStatus;
    }
}
