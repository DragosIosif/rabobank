package com.rabobank.assignment.batch.processor;

import com.rabobank.assignment.model.CustomerTransaction;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * Perform a validation of the transaction reference.
 * If the reference is not unique, the transaction is marked as invalid and the reason is provided
 */
public class CustomerTransactionReferenceValidator implements ItemProcessor<CustomerTransaction, CustomerTransaction> {
    static final String INVALID_REFERENCE = "Invalid reference";
    private final Set<Long> transactionReferences = new HashSet<>();

    @Override
    public CustomerTransaction process(final CustomerTransaction customerTransaction) {
        if (!customerTransaction.isProcessed()) {
            customerTransaction.setProcessed(true);
            if (transactionReferences.contains(customerTransaction.getReference())) {
                customerTransaction.setValid(false);
                customerTransaction.addReason(INVALID_REFERENCE);
            } else {
                transactionReferences.add(customerTransaction.getReference());
            }
        }
        return customerTransaction;
    }

}
