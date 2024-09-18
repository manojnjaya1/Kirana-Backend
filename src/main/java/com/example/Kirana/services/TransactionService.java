package com.example.Kirana.services;

import com.example.Kirana.models.Transaction;

import java.util.Map;

/**
 * Service interface for transaction-related operations, including currency rate caching and transaction recording.
 */
public interface TransactionService {

    /**
     * Fetches currency rates from an external API and caches the results.
     *
     * @return a map containing currency codes and their corresponding rates
     */
    Map<String, Object> fetchAndCacheCurrencyRates();

    /**
     * Records a transaction with concurrency control to ensure no duplicate processing.
     *
     * @param transaction the transaction details to be recorded
     * @return the recorded transaction, including any additional data or status
     */
    Transaction recordTransactionWithConcurrencyControl(Transaction transaction);
}
