
#  Doctor Appointment System


This project is a microservices-based doctor appointment booking system built using Spring Boot and Spring Cloud. It includes service discovery, security via JWT, REST APIs,RestDocs,testing units and externalized configuration using environment variables (.env files).


---

## ? Tech Stack

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Cloud Version**: `2024.0.1`
- **Spring Data JPA**
- **Spring Security + JWT**
- **MySQL**
- **Eureka Client**
- **JUnit 5 + Mockito**

---


## ? Project Structure

```
doctor-appointment-system/
?
??? src/
?   ??? main/
?   ?   ??? java/com/mahmoud/appointmentsystem/   # Core business logic
?   ?   ??? resources/
?   ?       ??? application.properties                  # Configuration
?   ?       ??? logback-spring.xml                  # Logging Configuration
?   ??? test/                                           # Unit and integration tests
??? pom.xml
??? README.md
??? x.env
```

---

## ?? Configuration

### `application.properties`

```properties
# Service Identity
All sensitive and dynamic values are managed using a `.env` file.

### ? Sample `.env`

```dotenv
SPRING_APPLICATION_NAME=user-service
SERVER_PORT=8081

# Eureka
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://localhost:8761/eureka/
EUREKA_INSTANCE_PREFERIPADDRESS=true

# MySQL DB
DB_URL=jdbc:mysql://localhost:3306/doctor_appointment
DB_USERNAME=root
DB_PASSWORD=your_secure_password

# JWT
JWT_SECRET_KEY=your_secure_secret
JWT_EXPIRATION=900000

# Mail (e.g. Gmail SMTP)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
MAIL_SMTP_AUTH=true
MAIL_SMTP_STARTTLS_ENABLE=true

# JPA and Profile
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=true
SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
SPRING_PROFILES_ACTIVE=dev
```

## ? Logging

- ? Structured Logging using **SLF4J** + **Logback**
    - Configurable via `logback-spring.xml`
    - Supports profile-specific logging behavior
    - Optional: rolling log files, separate logs per environment, colored console output

## ? Security

The service uses `spring-security` combined with `java-jwt` 

---

## ? Testing

- Uses **JUnit 5** for unit testing
- Uses **Mockito** for mocking service dependencies
- Includes `spring-security-test` for security layer testing

---

## ? Dependencies Overview (from `pom.xml`)

| Purpose                | Library                               |
|------------------------|----------------------------------------|
| Web Framework          | `spring-boot-starter-web`             |
| JPA ORM                | `spring-boot-starter-data-jpa`        |
| Security               | `spring-boot-starter-security`        |
| JWT Authentication     | `com.auth0:java-jwt`                  |
| Service Discovery      | `spring-cloud-starter-netflix-eureka-client` |
| Monitoring             | `spring-boot-starter-actuator`        |
| MySQL DB               | `mysql-connector-j`                   |
| Dev Tools              | `spring-boot-devtools`                |
| Testing                | `spring-boot-starter-test`, `mockito`, `junit-jupiter` |

---

## ? Running the Service

### Prerequisites

- Java 21
- Maven
- MySQL running on `localhost:3306` with DB named `doctor_appointment`
- Eureka server running on port `8761`
- user-service microservice  running on port `8081`

### Start Order

1. **Eureka Server**
2. **User Service**
3. **Appointment Service**

### Run via Maven:

```bash
mvn clean spring-boot:run
```

---

## ? Eureka & Appointment Service Integration

| Service                | Port   |
|------------------------|--------|
| Eureka Server          | `8761` |
| User Service           | `8081` |
| Appointment Service    | `8080` |

All services register with Eureka for load-balanced inter-service communication.

---

## ?? Author

**Mahmoud Ramadan**  
Email: [mahmoudramadan385@gmail.com](mailto:mahmoudramadan385@gmail.com)

---

## ?? License

This project is licensed under a proprietary license. For use or redistribution, contact the author.

---
