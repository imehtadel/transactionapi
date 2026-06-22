package com.awin.interview.transactionapi.validation;

import com.awin.interview.transactionapi.dto.request.CreateTransactionPartRequest;
import com.awin.interview.transactionapi.dto.request.CreateTransactionRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class ValidTransactionValidator implements ConstraintValidator<ValidTransaction, CreateTransactionRequest> {
    @Override
    public boolean isValid(CreateTransactionRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if(request == null){
            return true;
        }

        BigDecimal transactionPartsSalesTotal = request.parts().stream()
                .filter(p -> p.saleAmount()!=null)
                .map(CreateTransactionPartRequest::saleAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal transactionPartsCommissionTotal = request.parts().stream()
                .filter(p -> p.commissionAmount()!=null)
                .map(CreateTransactionPartRequest::commissionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return transactionPartsSalesTotal.compareTo(request.saleAmount()) == 0
                && transactionPartsCommissionTotal.compareTo(request.commissionAmount()) == 0;
    }
}
