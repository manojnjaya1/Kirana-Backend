package com.example.Kirana.CustomExceptions;


/**
 * Exception thrown when there is an error during JWT token generation.
 */
public class TokenGenerationException extends RuntimeException {
    public TokenGenerationException(String message) {
        super(message);
    }

    public TokenGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}

