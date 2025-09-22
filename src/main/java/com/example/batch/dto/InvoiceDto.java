package com.example.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InvoiceDto {
    private String codigoDeProveedor;
    private String codigoDeFactura;
    private BigDecimal importe;
    private String divisa;
    private LocalDate fechaDeVencimiento;
    private String iban;

}
