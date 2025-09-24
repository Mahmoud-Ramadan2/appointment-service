
#  Appointment service—Appointment System

The **Appointment service** is a core microservice in the *Appointment System*, responsible for booking and handling patient's and doctor's appointments and Acts as producer for notification service.     


---

##  Tech Stack

- **Java 21**
- **Spring Boot 3.4.5**
- **Spring Cloud Version**: `2024.0.1`
- **Spring Data JPA**
- **Spring Security + JWT**
- - **Lombok**
- **Spring Actuator**
- **OpenFeign**
- **Spring Kafka**
- **Eureka Client**
-  **API Gateway**
- **JUnit 5 + Mockito**
- **MySQL**

---


##  Project Structure

```
appointment-service/

   src/
         main/
                java/com/mahmoud/appointmentsystem/   # Core business logic
                resources/
                   application.properties                  # Configuration
                   logback-spring.xml                  # Logging Configuration
        test/                                           # Unit and integration tests
   pom.xml
   README.md
   x.env
```

---

##  Configuration

### `application.properties`

```properties
# Service Identity
All sensitive and dynamic values are managed using a `.env` file.

###  Sample `.env`

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

##  Logging

-  Structured Logging using **SLF4J** + **Logback**
    - Configurable via `logback-spring.xml`
    - Supports profile-specific logging behavior
    - Optional: rolling log files, separate logs per environment, colored console output

##  Security

The service uses `spring-security` combined with `java-jwt` 

---

##  Testing

- Uses **JUnit 5** for unit testing
- Uses **Mockito** for mocking service dependencies
- Includes `spring-security-test` for security layer testing

---

##  Dependencies Overview (from `pom.xml`)

| Purpose                | Library                                               |
|------------------------|-------------------------------------------------------|
| Web Framework          | `spring-boot-starter-web`                             |
| JPA ORM                | `spring-boot-starter-data-jpa`                        |
| Security               | `spring-boot-starter-security`                        |
| JWT Authentication     | `com.auth0:java-jwt`                                  |
|                OpenFeign        | `spring-cloud-starter-openfeign`                                                      |
| Service Discovery      | `spring-cloud-starter-netflix-eureka-client`          |
| Monitoring             | `spring-boot-starter-actuator`                        |
| MySQL DB               | `mysql-connector-j`                                   |
| Dev Tools              | `spring-boot-devtools`                                |
| Testing                | `spring-boot-starter-test`, `mockito`, `junit-jupiter` |

---

  ##  Running the Service

### Prerequisites

- Java 21
- Maven
- MySQL running on `localhost:3306` with DB named `doctor_appointment`
- Kafka & Zookeeper running (`localhost:9092`, `localhost:2181`)
- Eureka server running on port `8761`
- user-service  running on port `8081`
- notification-service  running on port `8083`
- gateway service running on port `8080`
#### Kafka Quick Start

- Running Kafka with Docker Compose

This project requires Kafka and Zookeeper.  
A preconfigured `docker-compose.kafka.yml` file is included in the root directory.

Start the services with:

```bash
docker compose -f docker-compose.kafka.yml up -d
````
- docker-compose.yml for kafka
```bash
# Here docker-compose file to install kafka

services:
 # 1. Zookeeper service
 zookeeper:
  image: confluentinc/cp-zookeeper:7.7.0
  container_name: zookeeper
  ports:
   - "2181:2181"
  environment:
   ZOOKEEPER_CLIENT_PORT: 2181
   ZOOKEEPER_TICK_TIME: 2000
  networks:
    - kafka-network
    
 # 2. kafka service
 kafka:
  image: confluentinc/cp-kafka:7.7.0
  container_name: kafka
  ports:
   - "9092:9092"
  environment:
   KAFKA_BROKER_ID: 1
   KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
   KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
   KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
  depends_on:
   - zookeeper
  networks:
    - kafka-network
    
networks:
 kafka-network:
  driver: bridge
 ```

### Start Order

1. **Eureka Server**
2. **gateway service**
3. **User Service**
4. **Appointment Service**
5. **Notification Service**
6. Other services

### Run via Maven:

```bash
mvn clean spring-boot:run
```

---

##  Eureka & Appointment Service Integration

| Service              | Port   |
|----------------------|--------|
| Eureka Server        | `8761` |
| User Service         | `8081` |
| Appointment Service  | `8082` |
| Notification Service | `8083`  |
| gateway service      | `8080` |

All services register with Eureka for load-balanced inter-service communication.

---

##  Author

**Mahmoud Ramadan**  
Email: [mahmoudramadan385@gmail.com](mailto:mahmoudramadan385@gmail.com)

---

##  License

This project is licensed under a proprietary license. For use or redistribution, contact the author.

---
