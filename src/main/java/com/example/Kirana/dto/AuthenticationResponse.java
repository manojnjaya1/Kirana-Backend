package com.example.Kirana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for handling authentication responses.
 * Contains details of the authentication result, including the username,
 * token, and any error messages.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationResponse {



    /**
     * The username of the authenticated user.
     */
    private String username;

    /**
     * The JWT token issued to the authenticated user.
     */
    private String token;

    /**
     * An error message indicating the reason for authentication failure.
     */
    private String errorMessage;

    /**
     * Sets the username in the response.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the token in the response.
     *
     * @param token the JWT token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Sets the error message in the response.
     *
     * @param errorMessage the error message to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Retrieves the username from the response.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the token from the response.
     *
     * @return the JWT token
     */
    public String getToken() {
        return token;
    }

    /**
     * Retrieves the error message from the response.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
