package com.example.Kirana.controllers;

import com.example.Kirana.dto.AuthenticationRequest;
import com.example.Kirana.dto.AuthenticationResponse;
import com.example.Kirana.dto.UserRegistrationRequest;
import com.example.Kirana.dto.UserRegistrationResponse;
import com.example.Kirana.models.Users;
import com.example.Kirana.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user registration and authentication.
 * Provides endpoints for user registration and login operations.
 */
@RestController
@RequestMapping("/api/auth")
public class UserRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    @Autowired
    private UserService userService;

    /**
     * Registers a new user.
     *
     * @param request The user registration details.
     * @return A ResponseEntity containing the registration response with the username.
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest request) {
        try {
            Users newUser = userService.registerUser(request);
            UserRegistrationResponse response = new UserRegistrationResponse();
            response.setUsername(newUser.getUsername());
            logger.info("User registered successfully: {}", newUser.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error registering user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserRegistrationResponse("An error occurred while registering the user."));
        }
    }

    /**
     * Authenticates a user and generates an authentication token.
     *
     * @param request The authentication request containing username and password.
     * @return A ResponseEntity containing the authentication response with the token.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = userService.authenticateUser(request);
            if (response.getToken() == null) {
                logger.warn("Authentication failed for user: {}", request.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthenticationResponse(null, null, "Invalid credentials"));
            }
            logger.info("User authenticated successfully: {}", request.getUsername());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            logger.error("Error authenticating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse(null, null, "Authentication failed"));
        }
    }
}
