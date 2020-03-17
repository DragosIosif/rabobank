package com.rabobank.assignment.batch.processor;

import com.rabobank.assignment.model.CustomerTransaction;
import org.springframework.batch.item.ItemProcessor;

/**
 * Perform a validation of the end balance of a given CustomerTransaction.
 * If the end balance is not correct, the transaction is marked as invalid and the reason is provided
 */
public class CustomerTransactionBalanceValidator implements ItemProcessor<CustomerTransaction, CustomerTransaction> {
    static final String INVALID_BALANCE = "Invalid Balance";

    @Override
    public CustomerTransaction process(final CustomerTransaction customerTransaction) {
        final double expected = customerTransaction.getStartBalance() + customerTransaction.getMutation();
        final Double actual = customerTransaction.getEndBalance();
        if (Double.compare(expected, actual) != 0) {
            customerTransaction.setValid(false);
            customerTransaction.addReason(INVALID_BALANCE);
        }
        return customerTransaction;
    }

}
