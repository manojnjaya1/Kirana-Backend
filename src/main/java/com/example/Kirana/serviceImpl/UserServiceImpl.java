package com.example.Kirana.serviceImpl;

import com.example.Kirana.dto.AuthenticationRequest;
import com.example.Kirana.dto.AuthenticationResponse;
import com.example.Kirana.dto.UserRegistrationRequest;
import com.example.Kirana.CustomExceptions.UserAuthenticationException;
import com.example.Kirana.models.Users;
import com.example.Kirana.repository.UserRepository;
import com.example.Kirana.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for handling user-related operations such as registration and authentication.
 * <p>
 * This service handles user registration by encoding passwords and saving user details to the repository.
 * It also manages user authentication by validating credentials and generating JWT tokens for authenticated users.
 * </p>
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    /**
     * Registers a new user with the provided registration details.
     * <p>
     * The password is encoded using BCrypt, and a default role ("ROLE_USER") is assigned if not provided.
     * </p>
     *
     * @param request The user registration details containing username, password, and optional role.
     * @return The registered user object with encoded password and assigned role.
     * @throws UserAuthenticationException if there is an error during registration.
     */
    @Override
    public Users registerUser(UserRegistrationRequest request) {
        try {
            Users user = new Users();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole() != null ? request.getRole() : "ROLE_USER"); // Default role

            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error registering user: {}", e.getMessage(), e);
            throw new UserAuthenticationException("Error registering user", e);
        }
    }

    /**
     * Authenticates a user and generates a JWT token if authentication is successful.
     * <p>
     * The method performs authentication using the provided credentials and generates a JWT token for authenticated users.
     * If authentication fails, an error message is returned.
     * </p>
     *
     * @param request The authentication request containing username and password.
     * @return An {@link AuthenticationResponse} containing the username and token if authentication is successful,
     *         or an error message if authentication fails.
     * @throws UserAuthenticationException if authentication fails or an error occurs during authentication.
     */
    public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();

            if (authentication.isAuthenticated()) {
                authenticationResponse.setUsername(request.getUsername());
                String token = jwtService.generateToken(request.getUsername());
                authenticationResponse.setToken(token);
                return authenticationResponse;
            } else {
                throw new UserAuthenticationException("Invalid username or password");
            }
        } catch (Exception e) {
            logger.error("Error authenticating user: {}", e.getMessage(), e);
            throw new UserAuthenticationException("Authentication failed", e);
        }
    }
}
