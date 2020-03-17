package com.rabobank.assignment.batch.processor;

import com.rabobank.assignment.model.CustomerTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class CustomerTransactionBalanceValidatorTest {

    private final CustomerTransactionBalanceValidator processor = new CustomerTransactionBalanceValidator();

    @Test
    void processValidPositiveMutation() {
        //given
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setStartBalance(11.0);
        transaction.setMutation(2.0);
        transaction.setEndBalance(13.0);

        //when
        transaction = processor.process(transaction);

        //then
        Assert.assertTrue(transaction.isValid());
        Assert.assertTrue(transaction.getReasons().isEmpty());
    }

    @Test
    void processValidNegativeMutation() {
        //given
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setStartBalance(11.0);
        transaction.setMutation(-2.0);
        transaction.setEndBalance(9.0);

        //when
        transaction = processor.process(transaction);

        //then
        Assert.assertTrue(transaction.isValid());
        Assert.assertTrue(transaction.getReasons().isEmpty());
    }

    @Test
    void processInValidPositiveMutation() {
        //given
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setStartBalance(11.0);
        transaction.setMutation(2.0);
        transaction.setEndBalance(12.0);

        //when
        transaction = processor.process(transaction);

        //then
        Assert.assertFalse(transaction.isValid());
        Assert.assertEquals(CustomerTransactionBalanceValidator.INVALID_BALANCE, transaction.getReasons().iterator().next());
    }

    @Test
    void processInValidNegativeMutation() {
        //given
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setStartBalance(11.0);
        transaction.setMutation(-2.0);
        transaction.setEndBalance(92.0);

        //when
        transaction = processor.process(transaction);

        //then
        Assert.assertFalse(transaction.isValid());
        Assert.assertEquals(1, transaction.getReasons().size());
        Assert.assertEquals(CustomerTransactionBalanceValidator.INVALID_BALANCE, transaction.getReasons().iterator().next());
    }
}