package com.example.Kirana.repository;

import com.example.Kirana.models.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Users} entities.
 * Provides methods for accessing and querying user data in the MongoDB database.
 */
@Repository
public interface UserRepository extends MongoRepository<Users, String> {

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return The {@link Users} object associated with the provided username.
     */
    Users findByUsername(String username);

    /**
     * Finds a user by their username and password.
     *
     * @param username The username of the user to find.
     * @param password The password of the user to find.
     * @return The {@link Users} object associated with the provided username and password.
     */
    Users findByUsernameAndPassword(String username, String password);
}
