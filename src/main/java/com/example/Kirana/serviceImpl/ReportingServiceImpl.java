package com.example.Kirana.serviceImpl;

import com.example.Kirana.dto.Report;
import com.example.Kirana.CustomExceptions.InvalidPeriodException;
import com.example.Kirana.models.Transaction;
import com.example.Kirana.repository.TransactionRepository;
import com.example.Kirana.services.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service implementation for generating financial reports based on transaction data.
 * <p>
 * This service generates reports that summarize the total credits, debits, and net flow of transactions
 * within a specified time period (e.g., weekly, monthly, yearly).
 * </p>
 */
@Service
public class ReportingServiceImpl implements ReportingService {

    private static final Logger logger = LoggerFactory.getLogger(ReportingServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Generates a financial report for the specified time period.
     * <p>
     * The report includes total credits, total debits, and net flow of transactions within the given period.
     * </p>
     *
     * @param period The time period for the report (e.g., "weekly", "monthly", "yearly").
     * @return A {@link Report} object containing the total credits, debits, net flow, and period.
     * @throws InvalidPeriodException if the provided period is invalid.
     */
    @Override
    public Report generateReport(String period) {
        try {
            LocalDateTime startDate = calculateStartDate(period);
            LocalDateTime endDate = LocalDateTime.now();

            List<Transaction> transactions = transactionRepository.findAllByTimestampBetween(startDate, endDate);

            double totalCredits = transactions.stream()
                    .filter(transaction -> "credit".equals(transaction.getType()))
                    .mapToDouble(Transaction::getConvertedAmount)
                    .sum();

            double totalDebits = transactions.stream()
                    .filter(transaction -> "debit".equals(transaction.getType()))
                    .mapToDouble(Transaction::getConvertedAmount)
                    .sum();

            double netFlow = totalCredits - totalDebits;

            return new Report(totalCredits, totalDebits, netFlow, period);
        } catch (Exception e) {
            logger.error("Error generating report for period {}: {}", period, e.getMessage(), e);
            throw new RuntimeException("Error generating report", e);
        }
    }

    /**
     * Calculates the start date based on the provided period.
     * <p>
     * The start date is calculated relative to the current date based on the specified period (e.g., "weekly", "monthly", "yearly").
     * </p>
     *
     * @param period The time period for which to calculate the start date.
     * @return The start date as a {@link LocalDateTime}.
     * @throws InvalidPeriodException if the provided period is invalid.
     */
    private LocalDateTime calculateStartDate(String period) {
        switch (period) {
            case "weekly":
                return LocalDateTime.now().minusWeeks(1);
            case "monthly":
                return LocalDateTime.now().minusMonths(1);
            case "yearly":
                return LocalDateTime.now().minusYears(1);
            default:
                logger.error("Invalid period specified: {}", period);
                throw new InvalidPeriodException("Invalid period: " + period);
        }
    }
}
