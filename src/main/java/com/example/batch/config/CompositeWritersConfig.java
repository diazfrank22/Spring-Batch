package com.example.batch.config;

import com.example.batch.domain.InvoiceEntity;
import com.example.batch.steps.writer.InvoiceRepositoryWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CompositeWritersConfig {

    @Bean
    public CompositeItemWriter<InvoiceEntity> compositeWriter(
            FlatFileItemWriter<InvoiceEntity> fileWriter,
            InvoiceRepositoryWriter repoWriter) {

        CompositeItemWriter<InvoiceEntity> writer = new CompositeItemWriter<>();
        writer.setDelegates(Arrays.asList(fileWriter, repoWriter));
        return writer;
    }
}
