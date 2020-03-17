package com.rabobank.assignment.service;

import com.rabobank.assignment.model.FailedTransaction;

import java.util.List;

/**
 * Service for CRUD operations for FailedTransaction objects
 */
public interface FailedTransactionService {
    /**
     * Find all the available FailedTransactions
     * @return the list of objects found
     */
    List<FailedTransaction> findAll();

    /**
     * Delete all the FailedTransactions
     */
    void deleteAll();
}
