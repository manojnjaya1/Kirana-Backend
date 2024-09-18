package com.example.Kirana.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Represents a financial transaction.
 * This model class is used to store and manage transaction data in the MongoDB database.
 */
@Data
@Document(collection = "transactions")
public class Transaction {

    /**
     * Unique identifier for the transaction.
     */
    @Id
    private String id;

    /**
     * The amount of money involved in the transaction.
     */
    private Double amount;

    /**
     * The type of transaction, which can be either 'credit' or 'debit'.
     */
    private String type;  // credit or debit

    /**
     * The currency in which the transaction amount is expressed.
     */
    private String currency;

    /**
     * The amount after currency conversion, if applicable.
     */
    private Double convertedAmount;

    /**
     * The timestamp when the transaction was recorded.
     */
    private LocalDateTime timestamp;
}
