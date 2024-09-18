package com.example.Kirana.serviceImpl;

import com.example.Kirana.dto.Report;
import com.example.Kirana.exceptions.TransactionProcessingException;
import com.example.Kirana.models.Transaction;
import com.example.Kirana.repository.TransactionRepository;
import com.example.Kirana.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
/**
 * Service implementation class for managing transactions.
 * Handles the recording of transactions with currency conversion and caching using Redis for concurrency control.
 * This class interacts with the TransactionRepository and provides functionalities like recording a transaction
 * with Redis locks to prevent race conditions, fetching currency rates from an external API, and caching currency rates.
 */

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Fetches the latest currency rates from an external API and caches them.
     * The rates are cached with the key "latestRates".
     *
     * @return a map containing currency rates where the key is the currency code and the value is the rate.
     * @throws TransactionProcessingException if the external API fails to provide valid currency rates.
     */
    @Override
    @Cacheable(value = "currencyRates", key = "'latestRates'")
    public Map<String, Object> fetchAndCacheCurrencyRates() {
        String url = "https://api.fxratesapi.com/latest";
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("rates")) {
                return (Map<String, Object>) response.get("rates");
            } else {
                throw new TransactionProcessingException("Failed to fetch valid currency rates from API.");
            }
        } catch (Exception e) {
            logger.error("Error fetching currency rates: {}", e.getMessage(), e);
            throw new TransactionProcessingException("Error fetching currency rates", e);
        }
    }

    /**
     * Records a transaction with concurrency control using Redis locks.
     * The transaction is processed only if the lock is successfully acquired.
     * After processing, the Redis lock is released.
     *
     * @param transaction The transaction to be recorded, including amount and currency details.
     * @return The saved transaction object with updated fields including converted amount and timestamp.
     * @throws TransactionProcessingException if the transaction is already being processed or currency rate is not available.
     */
    @Override
    public Transaction recordTransactionWithConcurrencyControl(Transaction transaction) {
        String lockKey = "lock::transaction::" + transaction.getId();
        Boolean lockAcquired = redisTemplate.opsForValue().setIfAbsent(lockKey, "locked");

        if (Boolean.TRUE.equals(lockAcquired)) {
            try {
                // Fetch currency rates
                Map<String, Object> currencyRates = fetchAndCacheCurrencyRates();
                Object rateObj = currencyRates.get(transaction.getCurrency());

                if (rateObj == null) {
                    throw new TransactionProcessingException("Currency rate not found for: " + transaction.getCurrency());
                }

                // Convert rateObj to Double if it is an Integer
                Double conversionRate = rateObj instanceof Number ? ((Number) rateObj).doubleValue() : null;

                if (conversionRate == null) {
                    throw new TransactionProcessingException("Invalid rate value for currency: " + transaction.getCurrency());
                }

                // Set converted amount and timestamp
                transaction.setConvertedAmount(transaction.getAmount() / conversionRate);
                transaction.setTimestamp(LocalDateTime.now());

                // Save the transaction
                return transactionRepository.save(transaction);

            } finally {
                redisTemplate.delete(lockKey); // Ensure lock is released after processing
            }
        } else {
            logger.warn("Transaction with ID {} is already being processed. Retry later.", transaction.getId());
            throw new TransactionProcessingException("Transaction is already being processed. Try again.");
        }
    }
}
