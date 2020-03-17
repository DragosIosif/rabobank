package com.rabobank.assignment.service;

import com.rabobank.assignment.model.FailedTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Default implementation of FailedTransactionService
 */
@Service
public class FailedTransactionServiceImpl implements FailedTransactionService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FailedTransactionServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<FailedTransaction> findAll() {
        return jdbcTemplate.query("SELECT * FROM FailedTransaction",
                (rs, row) -> new FailedTransaction(
                        rs.getLong(1),
                        rs.getString(2))
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM FailedTransaction");
    }
}
