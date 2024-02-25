# E-Comerce-Backend

# <img src="https://www.qindel.com/wp-content/uploads/2023/04/spring-boot.jpeg" width="80" height="80"> Spring Boot 3.2.2 [![Build Status](https://ci.spring.io/api/v1/teams/spring-framework/pipelines/spring-framework-6.2.x/jobs/build/badge)](https://ci.spring.io/teams/spring-framework/pipelines/spring-framework-6.1.x?groups=Build") [![Revved up by Develocity](https://img.shields.io/badge/Revved%20up%20by-Develocity-06A0CE?logo=Gradle&labelColor=02303A)](https://ge.spring.io/scans?search.rootProjectNames=spring)

Este es un proyecto es un API elaborado con spring boot, que simula el funcionamiento basico de un E-Comerce.
La API se encuentra documentada con swagger.
Autenticacion con JWT.

## Tecnologías Utilizadas

#### JAVA
#### Spring Boot 3.2.2
#### Postgrest
#### JWT
#### Swagger
#### Maven

## Forma de Uso

Para utilizar este codigo, debe clonar el repositorio o descargarlo. Descomentar esta linea #spring.jpa.hibernate.ddl-auto=create del archivo application.properties.
Crear una base de datos en postgrest con el nombre ecomerce y dentro de esta un esquema ecomerce.
Una vez realizado esto ejecutar el código desde su IDE favorito.
Para realizar pruebas desde postman en su entorno local el usurio que se crea por default es admin@gmail.com y la contraseña admin.
Url login http://localhost:8080/authentication
Parametros username y password.

## Documentation

Esta API se encuentra documentada con Swagger donde podra ver los enpoind con su respectiva documentacion.
Url de acceso http://localhost:8080/swagger-ui/index.html#/

## License

Licencia Open Source de [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
