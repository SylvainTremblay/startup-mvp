# Project Guidelines

## Overview
This project is a **Java** application built with **Gradle** and **Spring Boot**. 
It integrates with **Spring Cloud Azure**, **Splunk Observability**, and **Sabre APIs**. 
The project follows a modular structure for better maintainability and scalability.

---

## Project Structure

This project follows a standard Gradle project structure. 
Below is a brief overview of the key directories and files:
Each module has its own `build.gradle` file, and the root project has a `settings.gradle` file that includes all modules.

The module **booking-recovery-agent-client** will contain only the client api model that can be used 
by other apis.

The module **booking-recovery-agent-module** will contain the implementation of the booking recovery agent.

- **`build.gradle`**: Defines dependencies, plugins, and build configurations.
- **`gradle/libs.versions.toml`**: Centralized dependency version management.
- **`src/main/java`**: Contains application source code.
- **`src/test/java`**: Contains unit and integration tests.

### Package Structure
`com.westjet.ecomm.booking.recovery`
- **`actuator`**: Core business logic for booking recovery.
- **`configuration`**: Configuration classes for Spring Boot and third-party integrations.
- **`controller`**: REST controllers for exposing APIs.
- **`dao`**: Contains data access objects to interact with Sabre SOAP services. All SOAP calls are done using the SabreProxy class.
- **`exception`**: Custom exception. 
- **`mapper`**: Mapper classes for creating Sabre requests and converts Sabre responses to Java objects.
- **`model`**: Data models representing the structure of requests and responses.
- **`service`**: Service layer for business logic.
- **`util`**: Utility classes for common functionality.

---

## Running the Application
1. **Build the project**:
   ```bash
   ./gradlew clean build