# Simple Microservice with Spring Boot

## Overview
This is a simple microservice application built with Spring Boot. The application demonstrates basic microservice architecture and best practices.

## Project Highlights

- **Microservice Architecture**: Each business domain (Order, Product, Payment, User, Auth) is implemented as an independent Spring Boot service.
- **API Gateway**: Centralized entry point for routing and securing API requests.
- **Service Discovery**: Eureka Server for dynamic service registration and discovery.
- **Authentication & Authorization**: JWT-based security with a dedicated Auth Service.
- **Asynchronous Communication**: RabbitMQ for decoupled, event-driven messaging between services.
- **Data Persistence**: Spring Data JPA with relational databases (configured per service).
- **Exception Handling**: Centralized error handling for robust APIs.
- **API Documentation**: Swagger/OpenAPI for interactive API docs.
- **Configuration Management**: Externalized via `application.properties` for each service.
- **Testing**: JUnit-based unit and integration tests.
- **Containerization Ready**: Modular structure suitable for Docker and Kubernetes deployment (can be extended).
- **Tech Stack**:  
  - Java 17+  
  - Spring Boot  
  - Spring Security  
  - Spring Data JPA  
  - RabbitMQ  
  - Eureka (Netflix OSS)  
  - Swagger/OpenAPI  
  - Maven

## Architecture Diagram
![diagram](./image/Screenshot%202025-06-29%20at%2014.14.30.png)

## Order Flow


## Prerequisites
- Java 17 or higher
- Maven 3.6.x or higher
- Your favorite IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Getting Started

### Clone the Repository
```bash
git clone <repository-url>
cd microservice
```

### Build the Application
```bash
mvn clean install
```

### Run the Application
```bash
mvn spring-boot:run
```

The application will start on port 8080 by default.

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── microservice/
│   │               ├── controller/
│   │               ├── service/
│   │               ├── repository/
│   │               ├── model/
│   │               └── MicroserviceApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
```

## Features
- RESTful API endpoints
- Spring Boot auto-configuration
- Spring Data JPA for data persistence
- Spring Security 
- Swagger/OpenAPI documentation
- Asynchronous Communicaiton using RabbitMQ

## API Documentation
Once the application is running, you can access the API documentation at:
```
http://localhost:8080/swagger-ui.html
```

## Configuration
The application can be configured through `application.properties` or `application.yml`. Key configurations include:
- Server port
- Database connection
- Logging levels
- Other custom properties

## Testing
Run the test suite using:
```bash
mvn test
```

# Improvement List
- [x] Fix error handling in controller
- [x] setup auth-service
- [x] some issues in login function
- [ ] add swagger API docs
- [ ] add notification service
- [ ] add file-storage service

## Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
