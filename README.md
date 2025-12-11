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
