package com.example.batch.adapter;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import com.example.batch.domain.InvoiceEntity;
import com.example.batch.dto.InvoiceDto;

import java.util.List;
import java.util.stream.Collectors;

public class EntityToFileItemWriterAdapter<T extends InvoiceEntity> implements ItemWriter<T> {

    private final FlatFileItemWriter<InvoiceDto> delegate;

    public EntityToFileItemWriterAdapter(FlatFileItemWriter<InvoiceDto> delegate) {
        this.delegate = delegate;
    }


    @Override
    public void write(Chunk<? extends T> chunk) throws Exception {

        List<InvoiceDto> dtos = chunk.getItems()
                                     .stream()
                                     .map(this::toDto)
                                     .collect(Collectors
                                     .toList());

        delegate.open(new org.springframework.batch.item.ExecutionContext()); // ensure opened in step context normally handled by Spring

        delegate.write(new Chunk<>(dtos));
    }

    private InvoiceDto toDto(InvoiceEntity e) {

       return new InvoiceDto(e.getCodigoDeProveedor(),
                             e.getCodigoDeFactura(),
                             e.getImporte(),
                             e.getDivisa(),
                             e.getFechaDeVencimiento(),
                             e.getIban()
        );
    }
}

