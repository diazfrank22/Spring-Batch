package com.example.batch.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "facturas")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // si no hay PK natural, para tests; en producción adapta
    private Long id;

    @Column(name = "codigo_de_proveedor", length = 7)
    private String codigoDeProveedor;

    @Column(name = "codigo_de_factura", length = 20)
    private String codigoDeFactura;

    @Column(precision = 15, scale = 2)
    private BigDecimal importe;

    @Column(length = 3)
    private String divisa;

    @Column(name = "fecha_de_vencimiento")
    private LocalDate fechaDeVencimiento;

    @Column(length = 20)
    private String estado;

    @Column(name = "extraccion_pago")
    private Integer extraccionPago;

    @Column(name = "iban")
    private String iban;


}

