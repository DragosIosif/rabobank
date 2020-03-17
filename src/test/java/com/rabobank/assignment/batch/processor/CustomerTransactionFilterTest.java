package com.rabobank.assignment.batch.processor;

import com.rabobank.assignment.model.CustomerTransaction;
import com.rabobank.assignment.model.FailedTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class CustomerTransactionFilterTest {

    private final CustomerTransactionFilter processor = new CustomerTransactionFilter();

    @Test
    void processValid() {
        //given
        CustomerTransaction transaction = new CustomerTransaction();

        //when
        final FailedTransaction failedTransaction = processor.process(transaction);

        //then
        Assert.assertNull(failedTransaction);
    }

    @Test
    void processInvalidOneReason() {
        //given
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setReference(123L);
        transaction.setValid(false);
        final String reason = "reason here";
        transaction.addReason(reason);

        //when
        final FailedTransaction failedTransaction = processor.process(transaction);

        //then
        Assert.assertNotNull(failedTransaction);
        Assert.assertEquals(123L, failedTransaction.getReference().longValue());
        Assert.assertEquals(reason, failedTransaction.getReason());
    }

    @Test
    void processInvalidMultipleReasons() {
        //given
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setReference(123L);
        transaction.setValid(false);
        transaction.addReason("reason here");
        transaction.addReason("reason there");

        //when
        final FailedTransaction failedTransaction = processor.process(transaction);

        //then
        Assert.assertNotNull(failedTransaction);
        Assert.assertEquals(123L, failedTransaction.getReference().longValue());
        Assert.assertEquals("reason here; reason there", failedTransaction.getReason());
    }
}