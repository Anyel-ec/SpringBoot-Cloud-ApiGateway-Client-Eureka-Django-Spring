# Spring Cloud Gateway Project

Este proyecto es una API Gateway creada con Spring Cloud Gateway, que enruta peticiones hacia microservicios desarrollados en Django y Spring Boot. También está configurado para registrar el servicio en Eureka para descubrimiento de servicios y utilizar PostgreSQL como base de datos para autenticación en una red social.

## Requisitos

- Java 17
- Maven 3.x
- PostgreSQL
- Spring Boot 3.3.2
- Eureka Server (en el puerto 8761)

## Configuración del Proyecto

### Puerto del servidor
El servicio API Gateway corre en el puerto `8080`.

```properties
server.port=8080
```

### Integración con Eureka

El proyecto está configurado para registrarse y obtener servicios de Eureka.

```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.client.fetch-registry=true
eureka.client.register-with-eureka=true
```

### Base de datos PostgreSQL

El proyecto utiliza una base de datos PostgreSQL para la autenticación en una red social.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/auth_red_social
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create-drop
```

### Rutas de Spring Cloud Gateway

#### Servicio Django

Las peticiones hacia `/django/**` serán redirigidas al microservicio Django que corre en `localhost:8000`.

```properties
spring.cloud.gateway.routes[0].id=django-service
spring.cloud.gateway.routes[0].uri=http://localhost:8000
spring.cloud.gateway.routes[0].predicates[0]=Path=/django/**
spring.cloud.gateway.routes[0].filters[0]=RewritePath=/django/(?<segment>.*), /${segment}
```

#### Servicio Spring Boot

Las peticiones hacia `/springboot/**` serán redirigidas al microservicio Spring Boot que corre en `localhost:8082`.

```properties
spring.cloud.gateway.routes[1].id=my-microservice
spring.cloud.gateway.routes[1].uri=http://localhost:8082
spring.cloud.gateway.routes[1].predicates[0]=Path=/springboot/**
spring.cloud.gateway.routes[1].filters[0]=RewritePath=/springboot/(?<segment>.*), /${segment}
```

### Configuración de CORS

El Gateway permite peticiones desde el front-end alojado en `http://localhost:4200` y acepta varios métodos HTTP.

```properties
spring.cloud.gateway.globalcors.cors-configurations.[/**].allowed-origins=http://localhost:4200
spring.cloud.gateway.globalcors.cors-configurations.[/**].allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.cloud.gateway.globalcors.cors-configurations.[/**].allowed-headers=*
spring.cloud.gateway.globalcors.cors-configurations.[/**].allow-credentials=true
```

## Dependencias Clave

- **Spring Cloud Gateway**: Para gestionar el enrutamiento de peticiones.
- **Spring Cloud Netflix Eureka**: Para el registro de servicios y descubrimiento.
- **Spring Data JPA**: Para interacciones con la base de datos PostgreSQL.
- **Spring Boot Security**: Para la configuración de seguridad en la aplicación.
- **Lombok**: Para simplificar el código.
- **OpenAPI**: Para la documentación de la API.

## Ejecución

1. Asegúrate de tener un servidor Eureka corriendo en `localhost:8761`.
2. Configura la base de datos PostgreSQL en `localhost:5432`.
3. Ejecuta el proyecto utilizando Maven:

```bash
mvn spring-boot:run
```

El servicio estará disponible en `http://localhost:8080`.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue antes de realizar un pull request.

## Licencia

Este proyecto está bajo la licencia MIT.
