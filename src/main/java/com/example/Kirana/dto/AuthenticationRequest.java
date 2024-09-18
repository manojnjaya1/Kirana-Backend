package com.example.Kirana.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for handling authentication requests.
 * Contains the necessary credentials for a user to authenticate.
 */
@Data
public class AuthenticationRequest {

    /**
     * The username of the user attempting to authenticate.
     */
    private String username;

    /**
     * The password of the user attempting to authenticate.
     */
    private String password;

    /**
     * Validates the AuthenticationRequest object.
     * Throws an exception if any required fields are missing or invalid.
     *
     * @throws IllegalArgumentException if username or password is null or empty
     */
    public void validate() {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }
}
