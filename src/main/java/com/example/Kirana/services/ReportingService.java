package com.example.Kirana.services;

import com.example.Kirana.dto.Report;

/**
 * Service interface for generating financial reports based on transaction data.
 */
public interface ReportingService {

    /**
     * Generates a financial report for a given period.
     *
     * @param period the period for which the report is generated (e.g., "weekly", "monthly", "yearly")
     * @return a {@link Report} object containing the total credits, total debits, net flow, and the specified period
     */
    Report generateReport(String period);
}






















