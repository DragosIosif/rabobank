package com.rabobank.assignment.batch.processor;

import com.rabobank.assignment.model.CustomerTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTransactionReferenceValidatorTest {

    private CustomerTransactionReferenceValidator processor;

    @BeforeEach
    void setUp() {
        processor = new CustomerTransactionReferenceValidator();
    }

    @Test
    void processValid() {
        //given
        CustomerTransaction transaction1 = new CustomerTransaction();
        transaction1.setReference(123L);
        CustomerTransaction transaction2 = new CustomerTransaction();
        transaction2.setReference(12345L);

        //when
        transaction1 = processor.process(transaction1);
        transaction2 = processor.process(transaction2);

        //then
        Assert.assertTrue(transaction1.isValid());
        Assert.assertTrue(transaction1.getReasons().isEmpty());

        Assert.assertTrue(transaction2.isValid());
        Assert.assertTrue(transaction2.getReasons().isEmpty());
    }

    @Test
    void processInValid() {
        //given
        CustomerTransaction transaction1 = new CustomerTransaction();
        transaction1.setReference(123L);
        CustomerTransaction transaction2 = new CustomerTransaction();
        transaction2.setReference(123L);

        //when
        transaction1 = processor.process(transaction1);
        transaction2 = processor.process(transaction2);

        //then
        Assert.assertTrue(transaction1.isValid());
        Assert.assertTrue(transaction1.getReasons().isEmpty());

        Assert.assertFalse(transaction2.isValid());
        Assert.assertEquals(1, transaction2.getReasons().size());
        Assert.assertEquals(CustomerTransactionReferenceValidator.INVALID_REFERENCE, transaction2.getReasons().iterator().next());
    }
}