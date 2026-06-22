package com.awin.interview.transactionapi.dto.request;

import com.awin.interview.transactionapi.validation.ValidTransaction;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@ValidTransaction
public record CreateTransactionRequest(
        @NotNull @DecimalMin("0.00")BigDecimal saleAmount,
        @NotNull @DecimalMin("0.00")BigDecimal commissionAmount,
        @NotEmpty @Valid List<CreateTransactionPartRequest> parts

) {

}
