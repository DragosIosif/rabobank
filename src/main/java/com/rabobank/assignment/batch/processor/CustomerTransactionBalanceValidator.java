package com.rabobank.assignment.batch.processor;

import com.rabobank.assignment.model.CustomerTransaction;
import org.springframework.batch.item.ItemProcessor;

public class CustomerTransactionBalanceValidator implements ItemProcessor<CustomerTransaction, CustomerTransaction> {
    private static final String INVALID_BALANCE = "Invalid Balance";

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
