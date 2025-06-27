# QuizzApp Backend

Este proyecto es el backend de QuizzApp, una aplicación de cuestionarios desarrollada en Java con Spring Boot.

## Requisitos previos

- Java 17 o superior
- Maven 3.8+
- Docker (para base de datos MariaDB/MySQL)
- Git

## Clonación del repositorio

Clona este repositorio:

```bash
git clone https://github.com/ortbar/quizzApp.git
cd quizzApp
```

## Configuración de la base de datos (MariaDB/MySQL recomendado)

Se recomienda usar una base de datos MariaDB/MySQL. Puedes levantar una instancia rápidamente con Docker:

```bash
docker run --name quizzapp-mariadb -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=quizzapp -e MYSQL_USER=quizzapp -e MYSQL_PASSWORD=quizzapp -p 3306:3306 -d mariadb:latest
```

Asegúrate de que los datos de conexión en `src/main/resources/application.properties` sean:

```
spring.datasource.url=jdbc:mysql://localhost:3306/quizzapp
spring.datasource.username=quizzapp
spring.datasource.password=quizzapp
spring.jpa.hibernate.ddl-auto=update
```

## Instalación y ejecución

1. Compila el proyecto:
   ```bash
   ./mvnw clean install
   ```

2. Ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```
   O bien:
   ```bash
   java -jar target/quizzapp-0.0.1-SNAPSHOT.jar
   ```

## Endpoints y documentación

- La API expone endpoints RESTful para autenticación, usuarios, juegos y preguntas.
- La documentación Swagger está disponible en:
  ```
  http://localhost:8080/swagger-ui.html
  ```

## Datos de ejemplo

El archivo `src/main/resources/data.sql` contiene datos de ejemplo que se cargan automáticamente al iniciar la aplicación.

## Despliegue

- Puedes desplegar la aplicación en cualquier servidor compatible con Java.
- Configura correctamente las variables de entorno y la base de datos en `application.properties` para producción.

## Contacto

Para dudas o soporte, contacta al equipo de desarrollo o abre un issue en el repositorio.

