package com.rabobank.assignment.batch.processor;

import com.rabobank.assignment.model.CustomerTransaction;
import com.rabobank.assignment.model.FailedTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Filters invalid CustomerTransactions.
 * If a CustomerTransaction is valid, it will be ignored.
 * If a CustomerTransaction is invalid, it is transformed into a FailedTransaction
 */
public class CustomerTransactionFilter implements ItemProcessor<CustomerTransaction, FailedTransaction> {

    private static final Logger log = LoggerFactory.getLogger(CustomerTransactionFilter.class);

    @Override
    public FailedTransaction process(final CustomerTransaction customerTransaction) {
        if (customerTransaction.isValid()) {
            return null;
        } else {
            log.info("invalid: " + customerTransaction);
            final FailedTransaction failedTransaction = new FailedTransaction();
            failedTransaction.setReference(customerTransaction.getReference());
            failedTransaction.setReason(String.join("; ", customerTransaction.getReasons()));
            return failedTransaction;
        }
    }

}
