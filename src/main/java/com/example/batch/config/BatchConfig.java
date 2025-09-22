package com.example.batch.config;

import com.example.batch.domain.InvoiceEntity;
import com.example.batch.steps.processor.InvoiceItemProcessor;
import com.example.batch.steps.writer.InvoiceRepositoryWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final InvoiceRepositoryWriter repoWriter;
    private final CompositeItemWriter<InvoiceEntity> compositeWriter;

    public BatchConfig(JobRepository jobRepository,
                       PlatformTransactionManager transactionManager,
                       InvoiceRepositoryWriter repoWriter,
                       CompositeItemWriter<InvoiceEntity> compositeWriter) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.repoWriter = repoWriter;
        this.compositeWriter = compositeWriter;
    }

    @Bean
    public ItemProcessor<InvoiceEntity, InvoiceEntity> invoiceProcessor() {
        return new InvoiceItemProcessor();
    }

    @Bean
    public Step extractInvoicesStep(JpaPagingItemReader<InvoiceEntity> reader,
                                    ItemProcessor<InvoiceEntity, InvoiceEntity> processor,
                                    StepExecutionListener fileRenameListener) {
        return new StepBuilder("extractInvoicesStep", jobRepository)
                .<InvoiceEntity, InvoiceEntity>chunk(50, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(compositeWriter)
                .listener(fileRenameListener)
                .build();
    }

    @Bean
    public Job extractInvoicesJob(Step extractInvoicesStep) {
        return new JobBuilder("extractInvoicesJob", jobRepository)
                .start(extractInvoicesStep)
                .build();
    }
}
