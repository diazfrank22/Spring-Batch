package com.example.batch.steps.writer;

import com.example.batch.domain.InvoiceEntity;
import com.example.batch.repository.InvoiceRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceRepositoryWriter implements ItemWriter<InvoiceEntity> {

    private final InvoiceRepository repository;

    public InvoiceRepositoryWriter(InvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void write(Chunk<? extends InvoiceEntity> chunk) {

        List<InvoiceEntity> items = new ArrayList<>(chunk.getItems());

        repository.saveAll(items);
    }

}
