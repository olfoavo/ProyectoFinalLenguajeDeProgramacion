# sakila-orm

Mini CRUD con JDBC + HikariCP sobre la BD `sakila`.

## Requisitos
- Java 17
- Maven 3.9+
- MySQL 8 con BD `sakila` cargada

## Configuración
Exporta (o pon en `~/.zshrc`):
```bash
export SAKILA_DB_USER=root
export SAKILA_DB_PASS='TuClaveFuerte2025#'
export SAKILA_DB_URL='jdbc:mysql://127.0.0.1:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'

## Cómo ejecutar

### Variables de entorno
```bash
export SAKILA_DB_USER=root
export SAKILA_DB_PASS='TU_CLAVE'
export SAKILA_DB_URL='jdbc:mysql://127.0.0.1:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'

Build + Run

mvn -U -DskipTests=true clean package
mvn -q exec:java                 
mvn -q exec:java -Dexec.mainClass=com.gustavo.sakila.AppManagersDemo

Salida esperada

=== Demo Sakila: Managers (Language/Category/Film) ===
Registros -> language: 6 | category: 16 | film: 1000
Creado language_id=8
Actualizado? true
Borrado? true
OK.
