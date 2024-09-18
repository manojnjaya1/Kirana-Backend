package com.example.Kirana.serviceImpl;

import com.example.Kirana.CustomExceptions.UserNotFoundException;
import com.example.Kirana.models.UserPrincipal;
import com.example.Kirana.models.Users;
import com.example.Kirana.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service implementation for loading user-specific data for authentication purposes.
 * <p>
 * This service retrieves user details from the repository and provides them for Spring Security authentication.
 * </p>
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private UserRepository userRepo;

    /**
     * Loads user details by username.
     * <p>
     * Retrieves the user from the repository based on the provided username and returns a {@link UserPrincipal} object.
     * Throws {@link UsernameNotFoundException} if the user is not found.
     * </p>
     *
     * @param username the username of the user to be retrieved.
     * @return a {@link UserDetails} object containing user information.
     * @throws UsernameNotFoundException if the user with the specified username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username);
        if (user == null) {
            logger.error("User not found with username: {}", username);
            throw new UserNotFoundException("User not found with username: " + username);
        }
        return new UserPrincipal(user);
    }
}
