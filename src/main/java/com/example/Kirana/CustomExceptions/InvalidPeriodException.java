package com.example.Kirana.CustomExceptions;

/**
 * Custom exception to be thrown when an invalid period is provided for report generation.
 */
public class InvalidPeriodException extends RuntimeException {

    /**
     * Constructs a new InvalidPeriodException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidPeriodException(String message) {
        super(message);
    }
}
