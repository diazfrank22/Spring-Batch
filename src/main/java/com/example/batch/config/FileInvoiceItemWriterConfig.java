package com.example.batch.config;

import com.example.batch.domain.InvoiceEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.time.LocalDate;

@Configuration
public class FileInvoiceItemWriterConfig {

    @Bean
    @StepScope
    public FlatFileItemWriter<InvoiceEntity> invoiceFileWriter(
            @Value("#{jobParameters['fecha']}") String fechaParam,
            @Value("${app.output.dir}") String outputDir) {

        String fecha = (fechaParam == null) ? LocalDate.now().toString() : fechaParam;
        String tmpPath = outputDir + "/invoices_" + fecha + ".tmp";

        return new FlatFileItemWriterBuilder<InvoiceEntity>()
                .name("invoiceFileWriter")
                .resource(new FileSystemResource(tmpPath))
                .headerCallback(writer -> writer.write("proveedor,factura,importe,divisa,fecha_de_vencimiento,iban"))
                .lineAggregator(new DelimitedLineAggregator<InvoiceEntity>() {{
                    setDelimiter(",");
                    setFieldExtractor(new BeanWrapperFieldExtractor<>() {{
                        setNames(new String[]{
                                "codigoDeProveedor",
                                "codigoDeFactura",
                                "importe",
                                "divisa",
                                "fechaDeVencimiento",
                                "iban"
                        });
                    }});
                }})
                .build();
    }
}
