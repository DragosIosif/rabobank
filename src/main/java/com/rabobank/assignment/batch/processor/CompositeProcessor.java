package com.rabobank.assignment.batch.processor;

import com.rabobank.assignment.model.CustomerTransaction;
import com.rabobank.assignment.model.FailedTransaction;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CompositeProcessor {

    @Bean
    @StepScope
    public CompositeItemProcessor<CustomerTransaction, FailedTransaction> processor() {
        CompositeItemProcessor<CustomerTransaction, FailedTransaction> compositeProcessor = new CompositeItemProcessor<>();
        compositeProcessor.setDelegates(Arrays.asList(new CustomerTransactionBalanceValidator(), new CustomerTransactionReferenceValidator(), new CustomerTransactionFilter()));
        return compositeProcessor;
    }
}
