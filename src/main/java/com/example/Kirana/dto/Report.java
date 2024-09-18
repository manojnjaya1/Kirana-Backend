package com.example.Kirana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for generating financial reports.
 * This class encapsulates the details of a financial report including
 * total credits, total debits, net flow, and the reporting period.
 */
@Data
@AllArgsConstructor
public class Report {

    /**
     * The total amount of credits within the reporting period.
     */
    private double totalCredits;

    /**
     * The total amount of debits within the reporting period.
     */
    private double totalDebits;

    /**
     * The net flow calculated as totalCredits minus totalDebits.
     */
    private double netFlow;

    /**
     * The reporting period for which the report is generated.
     * Possible values include "weekly", "monthly", "yearly".
     */
    private String period;
}
