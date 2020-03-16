package com.rabobank.assignment.batch.writer;

import com.rabobank.assignment.model.FailedTransaction;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CustomerStatementWriterConfiguration {

    @Bean
    public JdbcBatchItemWriter<FailedTransaction> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<FailedTransaction>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO FailedTransaction (reference, reason) VALUES (:reference, :reason)")
                .dataSource(dataSource)
                .build();
    }
}
