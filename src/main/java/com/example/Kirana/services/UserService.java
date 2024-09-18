package com.example.Kirana.services;

import com.example.Kirana.dto.AuthenticationRequest;
import com.example.Kirana.dto.AuthenticationResponse;
import com.example.Kirana.dto.UserRegistrationRequest;
import com.example.Kirana.models.Users;

/**
 * Service interface for user-related operations, including user registration and authentication.
 */
public interface UserService {

    /**
     * Registers a new user with the given registration request details.
     *
     * @param request the user registration request containing username, password, and optional role
     * @return the registered user
     */
    Users registerUser(UserRegistrationRequest request);

    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param request the authentication request containing username and password
     * @return an authentication response including the username and JWT token if authentication is successful
     */
    AuthenticationResponse authenticateUser(AuthenticationRequest request);
}
