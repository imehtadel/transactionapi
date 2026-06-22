package com.awin.interview.transactionapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidTransactionValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransaction {
    String message() default "Transaction totals are not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
