package com.example.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

@Component
public class OutputFileStepExecutionListener implements StepExecutionListener {

    private final String outputDir;

    public OutputFileStepExecutionListener(@Value("${app.output.dir:./output}") String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // no-op
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // obtener fecha del jobParameter 'fecha' o fallback a hoy
        String fecha = stepExecution.getJobParameters().getString("fecha");
        if (fecha == null || fecha.isBlank()) {
            fecha = LocalDate.now().toString();
        }

        Path tmp = Paths.get(outputDir, "invoices_" + fecha + ".tmp");
        Path csv = Paths.get(outputDir, "invoices_" + fecha + ".csv");

        try {
            if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
                // mover tmp -> csv (si existe tmp)
                if (tmp.getParent() != null) {
                    Files.createDirectories(tmp.getParent());
                }
                if (Files.exists(tmp)) {
                    Files.move(tmp, csv, StandardCopyOption.REPLACE_EXISTING);
                }
                return ExitStatus.COMPLETED;
            } else {
                // si falló/el step no completó, borrar temporal si existe
                if (Files.exists(tmp)) {
                    Files.delete(tmp);
                }
                return ExitStatus.FAILED;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error gestionando fichero temporal: " + e.getMessage(), e);
        }
    }
}

