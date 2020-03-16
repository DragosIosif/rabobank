package com.rabobank.assignment.batch.reader;

import com.rabobank.assignment.model.CustomerTransaction;
import com.rabobank.assignment.storage.StorageService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class XmlReaderConfiguration {

    @Autowired
    private StorageService storageService;

    @Bean
    @StepScope
    public MultiResourceItemReader<CustomerTransaction> multiResourceXmlReader(FlatFileItemReader<CustomerTransaction> csvReader) {
        Resource[] resources = storageService.loadAsResourcesByExtension(".csv");

        return new MultiResourceItemReaderBuilder<CustomerTransaction>()
                .name("multiResourceXmlReader")
                .delegate(csvReader)
                .resources(resources)
                .build();
    }

    @Bean
    public StaxEventItemReader<CustomerTransaction> xmlReader() {
        return new StaxEventItemReaderBuilder<CustomerTransaction>()
                .name("xmlReader")
                .resource(new ClassPathResource("static/records.xml"))
                .addFragmentRootElements("record")
                .unmarshaller(customerStatementMarshaller())
                .build();
    }

    @Bean
    public XStreamMarshaller customerStatementMarshaller() {
        Map<String, Class<?>> aliases = new HashMap<>();
        aliases.put("record", CustomerTransaction.class);

        Map<Class<?>, String> attributeMappings = new HashMap<>();
        attributeMappings.put(CustomerTransaction.class, "reference");

        XStreamMarshaller marshaller = new XStreamMarshaller();

        marshaller.setAliases(aliases);
        marshaller.setUseAttributeFor(attributeMappings);
        marshaller.setMode(XStream.NO_REFERENCES);
        marshaller.setReflectionProvider(new PureJavaReflectionProvider());

        return marshaller;
    }
}
