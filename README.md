# 🏋️‍♂️ Appointment & Dynamic Pricing Service (CEN315 Term Project)

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.8-green?style=flat-square&logo=springboot)
![Coverage](https://img.shields.io/badge/Coverage-91%25-brightgreen?style=flat-square)
![Tests](https://img.shields.io/badge/Tests-Passing-success?style=flat-square)

This project is a RESTful API service designed for a fitness centre management system. It demonstrates advanced test engineering practices including TDD, Mutation Testing, and Property-Based Testing.

**Student:** Mehmet Kaplan
**Course:** CEN315 Introduction to Test Engineering

---

## 📖 About The Project

This system is built to handle the core operations of a fitness center with a focus on reliability and testing. Key features include:

- **Dynamic Pricing Engine:** Calculates prices based on membership tiers (Standard, Premium, Student) and class occupancy (Surge Pricing).
- **Real-time Capacity Management:** Prevents overbooking by checking class capacity before confirming reservations.
- **Robust Reservation System:** Supports booking and cancellation with automatic inventory updates.
- **Quality Assurance:** Fully tested with Unit, Integration, and Mutation tests.

---

## 🚀 Project Status: Week 1 & 2 Completed

### ✅ Week 1: Infrastructure & Core Logic
- [x] **Project Setup:** Spring Boot 3.5.8, Java 21, Maven.
- [x] **Database:** H2 (In-Memory) for testing, PostgreSQL (Docker) for production.
- [x] **Architecture:** Layered Architecture (Controller -> Service -> Repository).
- [x] **Core Logic:** `PricingService` implemented with **TDD**.
- [x] **API:** Basic endpoints for Members, Classes, and Reservations (with DELETE support).
- [x] **Unit Testing:** Mockito tests for Service layer isolation.

### ✅ Week 2: Advanced Testing & Quality Assurance
- [x] **Code Coverage:** Achieved **91% Instruction** / **100% Branch** coverage (JaCoCo).
- [x] **Mutation Testing:** Achieved **79% Mutation Coverage** in Service layer (PITest).
- [x] **API Automation:** Automated integration tests using **Postman & Newman**.
- [x] **Property-Based Testing:** Verified mathematical invariants using `jqwik`.
- [x] **Decision Tables:** Implemented parameterized tests for complex pricing rules.
- [x] **Model-Based Testing:** Verified state transitions for Reservations.

---

## 🔗 API Endpoints

### 👤 Members
- `POST /members` - Register a new member.
- `GET /members` - List all registered members.
- `GET /members/{id}` - Get details of a specific member.

### 🧘 Classes
- `POST /classes` - Schedule a new fitness class.
- `GET /classes` - List all scheduled classes.

### 🎫 Reservations
- `POST /reservations` - Make a reservation (Triggers dynamic pricing & capacity check).
- `DELETE /reservations/{id}` - Cancel a reservation (Restores capacity).

---

## ⚙️ How to Run

### 1. Prerequisites
* JDK 21
* Docker (Optional, for PostgreSQL)
* Node.js & Newman (For API automation)

### 2. Run Locally (with H2 Database)
The application uses H2 In-Memory database by default for ease of development.
```bash
./mvnw spring-boot:run


API URL: http://localhost:8080

H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:gymdb

3. Run with Docker (PostgreSQL)

To run the database in a container:

docker-compose up -d


🧪 How to Run Tests

Unit & Integration Tests

Run all JUnit tests (including TDD, Mocking, and Property-Based tests):

./mvnw test


Coverage Report (JaCoCo)

Generate the code coverage report:

./mvnw clean verify


The report will be available at: target/site/jacoco/index.html

Mutation Testing (PITest)

Run mutation tests to verify test quality:

./mvnw org.pitest:pitest-maven:mutationCoverage


The report will be available at: target/pit-reports/index.html

API Automation (Newman)

To run the Postman collection from the command line:

newman run tests/postman/CEN315_Gym.postman_collection.json


(Make sure the application is running before executing this command)

```eof
