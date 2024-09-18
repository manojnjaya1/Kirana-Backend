package com.example.Kirana.CustomExceptions;



/**
 * Exception thrown when there is an error during JWT token validation.
 */
public class TokenValidationException extends RuntimeException {
    public TokenValidationException(String message) {
        super(message);
    }

    public TokenValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
