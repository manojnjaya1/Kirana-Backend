package com.example.Kirana.CustomExceptions;

/**
 * Custom exception for handling user registration and authentication errors.
 */
public class UserAuthenticationException extends RuntimeException {

    public UserAuthenticationException(String message) {
        super(message);
    }

    public UserAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
