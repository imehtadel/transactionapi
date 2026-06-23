package com.awin.interview.transactionapi.controller;

import com.awin.interview.transactionapi.exception.TransactionNotFoundException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, List<String>> errors= new HashMap<>();
        if(ex.getBindingResult().getFieldErrors().isEmpty()){
            ex.getBindingResult().getAllErrors().stream()
                    .forEach(err -> {
                        errors.computeIfAbsent(err.getCode(), k -> new ArrayList<>()).add(err.getDefaultMessage());
                    });
        } else {
            ex.getBindingResult().getFieldErrors().stream()
                    .forEach(err -> {
                        errors.computeIfAbsent(err.getField(), k -> new ArrayList<>()).add(err.getDefaultMessage());
                    });
        }
        Map<String, Object> errorBody= new HashMap<>();
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Bad Request");
        errorBody.put("errors", errors);
        return ResponseEntity.badRequest().body(errorBody);
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalStateException(IllegalStateException ex) {
        Map<String, Object> errorBody = createErrorBodyMap(HttpStatus.BAD_REQUEST, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }


    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<Map<String, Object>> handleOptimisticLockingFailure(OptimisticLockingFailureException ex) {
        Map<String, Object> errorBody = createErrorBodyMap(HttpStatus.CONFLICT, "Transaction was modified by another request.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorBody);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> genericException(Exception ex){

        Map<String, Object> errorBody = createErrorBodyMap(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        Map<String, Object> errorBody = createErrorBodyMap(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable( HttpMessageNotReadableException ex) {
        Map<String, Object> errorBody = createErrorBodyMap(HttpStatus.BAD_REQUEST, "Request body is missing or malformed");

        return ResponseEntity.badRequest().body(errorBody);
    }

    private static Map<String, Object> createErrorBodyMap(HttpStatus status, String ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", status.value());
        errorBody.put("error", status.getReasonPhrase());
        errorBody.put("message", ex);
        return errorBody;
    }
}
