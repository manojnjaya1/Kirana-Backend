package com.example.Kirana.exceptions;

/**
 * Custom exception for handling transaction processing errors.
 */
public class TransactionProcessingException extends RuntimeException {

    public TransactionProcessingException(String message) {
        super(message);
    }

    public TransactionProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
