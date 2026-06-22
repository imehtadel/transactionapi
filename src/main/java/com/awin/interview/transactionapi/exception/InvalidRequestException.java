package com.awin.interview.transactionapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(HttpStatus httpStatus, String message) {
        super(message);
    }
}
