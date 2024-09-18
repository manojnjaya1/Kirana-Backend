package com.example.Kirana.repository;

import com.example.Kirana.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing {@link Transaction} entities.
 * Provides methods for accessing and querying transaction data in the MongoDB database.
 */
@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    /**
     * Retrieves a list of transactions that occurred within a specified time range.
     *
     * @param start The start date and time of the range.
     * @param end The end date and time of the range.
     * @return A list of {@link Transaction} objects that fall within the specified time range.
     */
    List<Transaction> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
