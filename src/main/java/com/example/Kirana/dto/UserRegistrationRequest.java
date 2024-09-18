package com.example.Kirana.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for user registration requests.
 * This class contains the necessary information required to register a new user.
 */
@Data
public class UserRegistrationRequest {

    /**
     * The username of the user to be registered.
     * This should be unique for each user.
     */
    private String username;

    /**
     * The password for the user account.
     * This should be securely hashed before storage.
     */
    private String password;

    /**
     * The role assigned to the user during registration.
     * This is optional and can be "ROLE_USER" or other roles as defined.
     * If not provided, a default role of "ROLE_USER" may be assigned.
     */
    private String role;
}
