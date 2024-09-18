package com.example.Kirana.controllers;

import com.example.Kirana.dto.Report;
import com.example.Kirana.services.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * REST controller for handling report-related operations.
 * Provides endpoints to generate and retrieve financial reports based on specified periods.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportingService reportingService;

    /**
     * Retrieves a financial report for the specified period.
     *
     * @param period The period for which the report is to be generated (e.g., "weekly", "monthly", "yearly").
     * @return A ResponseEntity containing the generated Report.
     * @throws IllegalArgumentException if the provided period is invalid.
     */
    @GetMapping("/{period}")
    public ResponseEntity<Report> getReport(@PathVariable String period) {
        try {
            logger.info("Generating report for period: {}", period);
            Report report = reportingService.generateReport(period);
            return ResponseEntity.ok(report);
        } catch (IllegalArgumentException e) {
            logger.error("Error generating report: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null); // Return a bad request response with no body
        }
    }
}
