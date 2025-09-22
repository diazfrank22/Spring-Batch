package com.example.batch.steps.reader;

import com.example.batch.domain.InvoiceEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class InvoiceItemReader {

    @Bean
    @StepScope
    public JpaPagingItemReader<InvoiceEntity> jpaInvoiceReader(EntityManagerFactory emf,
                                                               @Value("#{jobParameters['fecha']}") String fechaParam) {

        System.out.println("Fecha recibida en reader: " + fechaParam);
        fechaParam = "2023-03-15";

        LocalDate fecha = (fechaParam == null) ? LocalDate.now() : LocalDate.parse(fechaParam);
        String jpql = "SELECT i FROM InvoiceEntity i WHERE i.fechaDeVencimiento = :fecha AND i.extraccionPago = 0";

        Map<String, Object> params = new HashMap<>();
        params.put("fecha", fecha);

        return new JpaPagingItemReaderBuilder<InvoiceEntity>()
                .name("jpaInvoiceReader")
                .entityManagerFactory(emf)
                .pageSize(100)
                .queryString(jpql)
                .parameterValues(params)
                .build();
    }
}

