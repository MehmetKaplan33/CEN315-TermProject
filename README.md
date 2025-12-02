# 🏋️‍♂️ Appointment & Dynamic Pricing Service (CEN315 Term Project)

This project is a RESTful API service designed for a fitness centre. It handles member management, class scheduling, reservations, and implements dynamic pricing rules based on occupancy and membership types.

**Student:** Mehmet Kaplan
**Course:** CEN315 Introduction to Test Engineering

---

## 🚀 Project Status: Week 1 Completed
- [x] **Project Setup:** Spring Boot 3.5.8, Java 21, Maven.
- [x] **Database:** H2 (In-Memory) for testing, PostgreSQL (Docker) for production.
- [x] **Architecture:** Layered Architecture (Controller -> Service -> Repository).
- [x] **Core Logic:** `PricingService` implemented with **TDD**.
- [x] **API:** Basic endpoints for Members, Classes, and Reservations (with DELETE support).
- [x] **Unit Testing:** Mockito tests for Service layer isolation.

---

## 🛠️ Tech Stack & Tools
* **Core:** Java 21, Spring Boot 3.x
* **Database:** H2, PostgreSQL
* **Testing:** JUnit 5, Mockito, JaCoCo, PITest
* **Containerization:** Docker & Docker Compose

---

## ⚙️ How to Run

### 1. Prerequisites
* JDK 21
* Docker (Optional, for PostgreSQL)

### 2. Run Locally (with H2 Database)
The application uses H2 In-Memory database by default.
```bash
./mvnw spring-boot:run
API URL: http://localhost:8080

H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:gymdb

3. Run with Docker (PostgreSQL)
To run the database in a container:

Bash

docker-compose up -d
🧪 How to Run Tests
Unit Tests
Run all unit tests (including TDD and Mocking tests):

Bash

./mvnw test
Coverage Report (JaCoCo)
Generate the coverage report:

Bash

./mvnw clean verify
The report will be available at: target/site/jacoco/index.html
