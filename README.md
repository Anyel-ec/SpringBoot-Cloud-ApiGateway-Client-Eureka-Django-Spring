# Spring Cloud Gateway Project

This project is an API Gateway created with Spring Cloud Gateway, which routes requests to microservices developed in Django and Spring Boot. It is also configured to register with Eureka for service discovery and uses PostgreSQL as a database for social network authentication.

## Requirements

- Java 17
- Maven 3.x
- PostgreSQL
- Spring Boot 3.3.2
- Eureka Server (running on port 8761)

## Project Configuration

### Server Port
The API Gateway service runs on port `8080`.

```properties
server.port=8080
```

### Eureka Integration

The project is configured to register with and fetch services from Eureka.

```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.client.fetch-registry=true
eureka.client.register-with-eureka=true
```

### PostgreSQL Database

The project uses a PostgreSQL database for authentication in a social network.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/auth_red_social
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create-drop
```

### Spring Cloud Gateway Routes

#### Django Service

Requests to `/django/**` are routed to the Django microservice running on `localhost:8000`.

```properties
spring.cloud.gateway.routes[0].id=django-service
spring.cloud.gateway.routes[0].uri=http://localhost:8000
spring.cloud.gateway.routes[0].predicates[0]=Path=/django/**
spring.cloud.gateway.routes[0].filters[0]=RewritePath=/django/(?<segment>.*), /${segment}
```

#### Spring Boot Service

Requests to `/springboot/**` are routed to the Spring Boot microservice running on `localhost:8082`.

```properties
spring.cloud.gateway.routes[1].id=my-microservice
spring.cloud.gateway.routes[1].uri=http://localhost:8082
spring.cloud.gateway.routes[1].predicates[0]=Path=/springboot/**
spring.cloud.gateway.routes[1].filters[0]=RewritePath=/springboot/(?<segment>.*), /${segment}
```

### CORS Configuration

The Gateway allows requests from the front-end hosted at `http://localhost:4200` and accepts multiple HTTP methods.

```properties
spring.cloud.gateway.globalcors.cors-configurations.[/**].allowed-origins=http://localhost:4200
spring.cloud.gateway.globalcors.cors-configurations.[/**].allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.cloud.gateway.globalcors.cors-configurations.[/**].allowed-headers=*
spring.cloud.gateway.globalcors.cors-configurations.[/**].allow-credentials=true
```

## Key Dependencies

- **Spring Cloud Gateway**: For managing request routing.
- **Spring Cloud Netflix Eureka**: For service registration and discovery.
- **Spring Data JPA**: For interactions with the PostgreSQL database.
- **Spring Boot Security**: For configuring security in the application.
- **Lombok**: To simplify the code.
- **OpenAPI**: For API documentation.

## Running the Project

1. Make sure you have a Eureka server running at `localhost:8761`.
2. Set up the PostgreSQL database at `localhost:5432`.
3. Run the project using Maven:

```bash
mvn spring-boot:run
```

The service will be available at `http://localhost:8080`.

## Contributions

Contributions are welcome. Please open an issue before submitting a pull request.

## License

This project is licensed under the MIT License.
