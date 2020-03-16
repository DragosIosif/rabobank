package com.rabobank.assignment.batch.reader;

import com.rabobank.assignment.model.CustomerTransaction;
import com.rabobank.assignment.storage.StorageService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class CsvReaderConfiguration {


    @Autowired
    private StorageService storageService;

    @Bean
    public FlatFileItemReader<CustomerTransaction> csvReader() {
        return new FlatFileItemReaderBuilder<CustomerTransaction>()
                .name("customerStatementCsvReader")
                .delimited()
                .names("reference", "accountNumber", "description", "startBalance", "mutation", "endBalance")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<CustomerTransaction>() {{
                    setTargetType(CustomerTransaction.class);
                }})
                .linesToSkip(1)
                .build();
    }

    @Bean
    @StepScope
    public MultiResourceItemReader<CustomerTransaction> multiResourceCsvReader(FlatFileItemReader<CustomerTransaction> csvReader) {
        Resource[] resources = storageService.loadAsResourcesByExtension(".csv");
        return new MultiResourceItemReaderBuilder<CustomerTransaction>()
                .name("multiResourceCsvReader")
                .delegate(csvReader)
                .resources(resources)
                .build();
    }
}
