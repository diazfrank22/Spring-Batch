package com.example.batch.repository;

import com.example.batch.domain.InvoiceEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    // Selección por fecha y extraccion_pago = 0 (para paginar si fuese necesario)
    @Query("SELECT i FROM InvoiceEntity i WHERE i.fechaDeVencimiento = :fecha AND i.extraccionPago = 0")
    List<InvoiceEntity> findByFechaVencimientoAndNotExtracted(@Param("fecha") LocalDate fecha);

    // Alternativamente: método para actualizar en lote (JPQL)
    @Modifying
    @Query("UPDATE InvoiceEntity i SET i.extraccionPago = 1 WHERE i.codigoDeProveedor = :prov AND i.codigoDeFactura = :fact")
    int markAsExtracted(@Param("prov") String proveedor, @Param("fact") String factura);
}

