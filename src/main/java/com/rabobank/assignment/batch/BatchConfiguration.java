package com.rabobank.assignment.batch;

import com.rabobank.assignment.model.CustomerTransaction;
import com.rabobank.assignment.model.FailedTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1(MultiResourceItemReader<CustomerTransaction> multiResourceCsvReader, CompositeItemProcessor<CustomerTransaction, FailedTransaction> processor, JdbcBatchItemWriter<FailedTransaction> writer) {
        return stepBuilderFactory.get("step1")
                .<CustomerTransaction, FailedTransaction>chunk(10)
                .reader(multiResourceCsvReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step step2(MultiResourceItemReader<CustomerTransaction> multiResourceXmlReader, CompositeItemProcessor<CustomerTransaction, FailedTransaction> processor, JdbcBatchItemWriter<FailedTransaction> writer) {
        return stepBuilderFactory.get("step2")
                .<CustomerTransaction, FailedTransaction>chunk(10)
                .reader(multiResourceXmlReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importStatementJob(JobCompletionNotificationListener listener, Step step1, Step step2) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .next(step2)
                .end()
                .build();
    }
}
