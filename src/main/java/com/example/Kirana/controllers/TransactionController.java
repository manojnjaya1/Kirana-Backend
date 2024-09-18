package com.example.Kirana.controllers;

import com.example.Kirana.models.Transaction;
import com.example.Kirana.services.TransactionService;
import io.github.bucket4j.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling transaction-related operations.
 * Provides endpoints to create transactions with rate limiting and concurrency control.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private Bucket transactionBucket;
    @Autowired
    private TransactionService transactionService;

    /**
     * Creates a new transaction with rate limiting and concurrency control.
     *
     * @param transaction The transaction details to be created.
     * @return A ResponseEntity containing the created transaction or an error message.
     */
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        if (transactionBucket.tryConsume(1)) {
            try {
                logger.info("Processing transaction: {}", transaction);
                Transaction savedTransaction = transactionService.recordTransactionWithConcurrencyControl(transaction);
                logger.info("Transaction successfully processed: {}", savedTransaction);
                return ResponseEntity.ok(savedTransaction);
            } catch (Exception e) {
                logger.error("Error processing transaction: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the transaction.");
            }
        } else {
            logger.warn("Rate limit exceeded for transaction creation.");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Rate limit exceeded. You can only request 10 times in an hour. Please try again later.");
        }
    }
}
