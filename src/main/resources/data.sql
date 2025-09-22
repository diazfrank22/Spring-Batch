INSERT INTO facturas (codigo_de_proveedor,codigo_de_factura,importe,divisa,fecha_de_vencimiento,estado,extraccion_pago,iban)
VALUES ('PROV001','FACT001',100.00,'EUR', DATE '2023-03-15','Pendiente',0,'valor1');

INSERT INTO facturas (codigo_de_proveedor,codigo_de_factura,importe,divisa,fecha_de_vencimiento,estado,extraccion_pago,iban)
VALUES ('PROV002','FACT002',200.00,'USD', DATE '2023-03-16','Pendiente',0,'valor2');

INSERT INTO facturas (codigo_de_proveedor,codigo_de_factura,importe,divisa,fecha_de_vencimiento,estado,extraccion_pago,iban)
VALUES ('PROV003','FACT003',300.00,'EUR', DATE '2023-03-15','Pendiente',0,'valor3');

INSERT INTO facturas (codigo_de_proveedor,codigo_de_factura,importe,divisa,fecha_de_vencimiento,estado,extraccion_pago,iban)
VALUES ('PROV001','FACT004',400.00,'USD', DATE '2023-03-17','Pagada',1,'valor4');
