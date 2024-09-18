package com.example.Kirana.CustomExceptions;


/**
 * Custom exception to be thrown when a user is not found in the repository.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
