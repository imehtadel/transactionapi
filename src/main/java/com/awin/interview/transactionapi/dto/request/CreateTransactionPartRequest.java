package com.awin.interview.transactionapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransactionPartRequest(
        @NotNull @DecimalMin("0.00")BigDecimal saleAmount,
        @NotNull @DecimalMin("0.00")BigDecimal commissionAmount
) {
}
