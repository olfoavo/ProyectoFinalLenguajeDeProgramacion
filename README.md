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


sakila-orm · ORM/CRUD Java 17 + MySQL Sakila

Autor: Gustavo
Repositorio destino: github.com/olfoavo/ProyectoFinalLenguajeDeProgramacion

Pequeño demo de ORM/JDBC sobre la base de ejemplo Sakila (MySQL).
Incluye dos apps de consola:
	•	AppManagersDemo: CRUD de language, category y consulta de film.
	•	AppFilmActorDemo: gestión de la relación film_actor (agregar, quitar, listar actores por película).

  .
├── pom.xml
├── README.md
├── .env.example                 # ejemplo de variables
└── src
    └── main
        ├── java
        │   └── com/gustavo/sakila
        │       ├── AppManagersDemo.java
        │       ├── AppFilmActorDemo.java
        │       ├── manager
        │       │   ├── BaseManager.java
        │       │   ├── LanguageManager.java
        │       │   ├── CategoryManager.java
        │       │   ├── FilmManager.java
        │       │   └── FilmActorManager.java
        │       ├── repo
        │       │   ├── CrudRepository.java
        │       │   ├── LanguageRepository.java
        │       │   ├── CategoryRepository.java
        │       │   ├── FilmRepository.java
        │       │   ├── FilmActorRepository.java
        │       │   └── ActorRepository.java
        │       └── model
        │           ├── Language.java
        │           ├── Category.java
        │           ├── Film.java
        │           ├── Actor.java
        │           └── FilmActor.java
        └── resources


Configuración de conexión (variables de entorno)
SAKILA_DB_URL="jdbc:mysql://127.0.0.1:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
SAKILA_DB_USER="root"
SAKILA_DB_PASS="TuClaveFuerte2025#"

Export rápido (macOS/Linux):
export SAKILA_DB_URL="jdbc:mysql://127.0.0.1:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
export SAKILA_DB_USER="root"
export SAKILA_DB_PASS="TuClaveFuerte2025#"

Instalar base Sakila (resumen)
	1.	Instala MySQL 8.x.
	2.	Carga schema y data de Sakila.
	3.	Valida conteos:

USE sakila;
SELECT COUNT(*) FROM language;   -- 6
SELECT COUNT(*) FROM category;   -- 16
SELECT COUNT(*) FROM film;       -- ~1000


Compilación y ejecución

# Compilar (sin tests)
mvn -U -DskipTests=true clean package

# Demo CRUD de managers (language/category/film)
mvn -q exec:java -Dexec.mainClass=com.gustavo.sakila.AppManagersDemo

# Demo relación film_actor
mvn -q exec:java -Dexec.mainClass=com.gustavo.sakila.AppFilmActorDemo

Salida esperada (ejemplo AppManagersDemo):
=== Demo Sakila: Managers (Language/Category/Film) ===
Registros -> language: 6 | category: 16 | film: 1000
Creado language_id=12
Actualizado? true
Borrado? true
OK.

Diseño / Arquitectura
	•	model/: POJOs que representan tablas (Language, Category, Film, Actor, FilmActor).
	•	repo/: Acceso JDBC con SQL parametrizado; implementa CrudRepository<T,ID>:
	•	create(T), findById(ID), findAll(), update(T), delete(ID).
	•	manager/: Lógica de negocio encima de los repositorios:
	•	LanguageManager: crear/renombrar/eliminar/listar idiomas.
	•	CategoryManager: crear/renombrar/eliminar/listar categorías.
	•	FilmManager: consultas básicas de películas.
	•	FilmActorManager: addActorToFilm, removeActorFromFilm, findActorsByFilm.
	•	apps: AppManagersDemo y AppFilmActorDemo muestran el uso de los managers.

Ejemplo de insert (repositorio):

final String sql = "INSERT INTO language(name) VALUES (?)";
try (var con = ds.getConnection();
     var ps  = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
  ps.setString(1, entity.getName());
  ps.executeUpdate();
  try (var rs = ps.getGeneratedKeys()) {
    if (rs.next()) return rs.getInt(1);
  }
}
return -1;

Actores por película:
final String sql = "SELECT actor_id FROM film_actor WHERE film_id = ?";
try (var con = ds.getConnection();
     var ps  = con.prepareStatement(sql)) {
  ps.setInt(1, filmId);
  try (var rs = ps.executeQuery()) {
    var ids = new ArrayList<Integer>();
    while (rs.next()) ids.add(rs.getInt(1));
    return ids;
  }
}

Problemas comunes (Troubleshooting)
	•	Access denied for user 'root'@'127.0.0.1' (using password: NO)
No exportaste SAKILA_DB_USER/SAKILA_DB_PASS. Vuelve a exportar y verifica con echo $SAKILA_DB_USER.
	•	Zona horaria/SSL
Usa este URL JDBC recomendado:
jdbc:mysql://127.0.0.1:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
	•	non-fast-forward al hacer push
El remoto ya tiene commits; rebasea antes de subir:

git pull --rebase origin main
git push -u origin main


Guía rápida de Git 

# Config autor (Gustavo)
git config user.name  "Gustavo"
git config user.email "gustavo-08medina@hotmail.com"

# Inicializar y primer commit
git init
git add .
git commit -m "Sakila ORM: CRUD + managers + demos"

# Remoto del repositorio final (SSH recomendado)
git remote add origin git@github.com:olfoavo/ProyectoFinalLenguajeDeProgramacion.git

# Asegurar rama principal y subir
git branch -M main
git pull --rebase origin main || true
git push -u origin main


Licencia

MIT. La base Sakila es un dataset educativo de MySQL.
