package com.example.Kirana.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration class for setting up rate limiting using Bucket4j.
 * This class defines a rate limit bucket to control the rate of requests.
 */
@Configuration
public class RateLimitConfig {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitConfig.class);

    /**
     * Creates a Bucket bean with a rate limit of 10 requests per minute.
     *
     * @return A Bucket instance configured with the specified rate limits.
     */
    @Bean
    public Bucket createTransactionBucket() {
        try {
            // Define a rate limit of 10 requests per minute
            Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)));
            Bucket bucket = Bucket4j.builder().addLimit(limit).build();
            logger.info("Rate limit bucket created with limit: 10 requests per minute");
            return bucket;
        } catch (Exception e) {
            logger.error("Failed to create rate limit bucket", e);
            throw new RuntimeException("Error creating rate limit bucket", e);
        }
    }
}
