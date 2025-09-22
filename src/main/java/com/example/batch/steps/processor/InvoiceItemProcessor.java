package com.example.batch.steps.processor;

import com.example.batch.domain.InvoiceEntity;
import org.springframework.batch.item.ItemProcessor;

public class InvoiceItemProcessor implements ItemProcessor<InvoiceEntity, InvoiceEntity> {

    @Override
    public InvoiceEntity process(InvoiceEntity item) {
        // marcar como extraída
        item.setExtraccionPago(1);

        return item;
    }
}
