# facturas-batch
Batch que extrae facturas por fecha de vencimiento y actualiza extraccion_pago.

## Requisitos
- Java 17
- Maven
- Oracle JDBC driver (en producción) / H2 para tests

## Build
mvn clean package

## Ejecutar local (H2 in-memory)
mvn spring-boot:run -Dspring-boot.run.arguments="--fecha=2023-03-15"

## Ejecutar jar
java -jar target/facturas-batch-0.0.1-SNAPSHOT.jar --fecha=2023-03-15

## Fichero de salida
Se genera en: `./output/invoices_<fecha>.csv` (o ruta configurada en app.output.dir)


