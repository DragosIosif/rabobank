package com.rabobank.assignment.batch;

import com.rabobank.assignment.model.CustomerTransaction;
import com.rabobank.assignment.model.FailedTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for batch processing
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step csvStep(MultiResourceItemReader<CustomerTransaction> multiResourceCsvReader, CompositeItemProcessor<CustomerTransaction, FailedTransaction> processor, JdbcBatchItemWriter<FailedTransaction> writer) {
        return stepBuilderFactory.get("csvStep")
                .<CustomerTransaction, FailedTransaction>chunk(10)
                .reader(multiResourceCsvReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step xmlStep(MultiResourceItemReader<CustomerTransaction> multiResourceXmlReader, CompositeItemProcessor<CustomerTransaction, FailedTransaction> processor, JdbcBatchItemWriter<FailedTransaction> writer) {
        return stepBuilderFactory.get("xmlStep")
                .<CustomerTransaction, FailedTransaction>chunk(10)
                .reader(multiResourceXmlReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importStatementJob(Step csvStep, Step xmlStep) {
        return jobBuilderFactory.get("validateStatementsJob")
                .incrementer(new RunIdIncrementer())
                .flow(csvStep)
                .next(xmlStep)
                .end()
                .build();
    }
}
