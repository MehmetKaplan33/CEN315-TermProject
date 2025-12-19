# 🏋️‍♂️ Appointment & Dynamic Pricing Service (CEN315 Term Project)

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.8-green?style=flat-square&logo=springboot)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue?style=flat-square&logo=docker)
![Coverage](https://img.shields.io/badge/Coverage-91%25-brightgreen?style=flat-square)
![Tests](https://img.shields.io/badge/Tests-Passing-success?style=flat-square)

This project is a RESTful API service designed for a fitness centre management system. It demonstrates advanced test engineering practices including TDD, Mutation Testing, Property-Based Testing, and Formal Verification.

**Student:** Mehmet Kaplan  
**Course:** CEN315 Introduction to Test Engineering

---

## 📖 About The Project

This system is built to handle the core operations of a fitness center with a focus on reliability, security, and scalability. Key features include:

- **Dynamic Pricing Engine:** Calculates prices based on membership tiers (Standard, Premium, Student) and class occupancy (Surge Pricing).
- **Real-time Capacity Management:** Prevents overbooking by checking class capacity before confirming reservations using Design by Contract.
- **Robust Reservation System:** Supports booking and cancellation with automatic inventory updates.
- **Quality Assurance:** Fully tested with Unit, Integration, Mutation, Performance, and Security tests.

---

## 🚀 Project Status: Weeks 1-3 Completed

### ✅ Week 1: Infrastructure & Core Logic
- [x] **Project Setup:** Spring Boot 3.5.8, Java 21, Maven.
- [x] **Database:** H2 (In-Memory) for testing, PostgreSQL (Docker) for production.
- [x] **Architecture:** Layered Architecture (Controller -> Service -> Repository).
- [x] **Core Logic:** `PricingService` implemented with **TDD**.
- [x] **API:** Basic endpoints for Members, Classes, and Reservations.
- [x] **Unit Testing:** Mockito tests for Service layer isolation.

### ✅ Week 2: Advanced Testing & Quality Assurance
- [x] **Code Coverage:** Achieved **91% Instruction** / **100% Branch** coverage (JaCoCo).
- [x] **Mutation Testing:** Achieved **79% Mutation Coverage** in Service layer (PITest).
- [x] **API Automation:** Automated integration tests using **Postman & Newman**.
- [x] **Property-Based Testing:** Verified mathematical invariants using `jqwik`.
- [x] **Decision Tables:** Implemented parameterized tests for complex pricing rules.
- [x] **Model-Based Testing:** Verified state transitions for Reservations.

### ✅ Week 3: Verification, Security & DevOps
- [x] **Performance Testing:** Conducted Load tests (50 concurrent users) using **k6** with <7ms p95 latency.
- [x] **Security Testing:** Automated vulnerability scanning with **OWASP ZAP** & STRIDE Threat Modeling.
- [x] **Formal Verification:** Applied **Design by Contract** (Spring Assert) and **TLA+** model checking specifications.
- [x] **Docker Infrastructure:** Production-ready containerization with Multi-stage builds and PostgreSQL integration.

---

## 🔗 API Endpoints

<table>
<tr>
<th>Category</th>
<th>Method</th>
<th>Endpoint</th>
<th>Description</th>
</tr>
<tr>
<td rowspan="3">👤 <strong>Members</strong></td>
<td><code>POST</code></td>
<td><code>/members</code></td>
<td>Register a new member</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/members</code></td>
<td>List all registered members</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/members/{id}</code></td>
<td>Get details of a specific member</td>
</tr>
<tr>
<td rowspan="2">🧘 <strong>Classes</strong></td>
<td><code>POST</code></td>
<td><code>/classes</code></td>
<td>Schedule a new fitness class</td>
</tr>
<tr>
<td><code>GET</code></td>
<td><code>/classes</code></td>
<td>List all scheduled classes</td>
</tr>
<tr>
<td rowspan="2">🎫 <strong>Reservations</strong></td>
<td><code>POST</code></td>
<td><code>/reservations</code></td>
<td>Make a reservation (Triggers dynamic pricing & capacity check)</td>
</tr>
<tr>
<td><code>DELETE</code></td>
<td><code>/reservations/{id}</code></td>
<td>Cancel a reservation (Restores capacity)</td>
</tr>
</table>

---

## ⚙️ How to Run

### 📋 1. Prerequisites

- ☕ **JDK 21** or higher
- 🐳 **Docker & Docker Compose** (Recommended for production environment)
- 📦 **Node.js & Newman** (For API automation testing)
- 🚀 **k6** (For performance testing)

---

### 🐳 2. Run with Docker (Recommended)

The project includes a production-ready `docker-compose` setup with PostgreSQL.

```bash
# Build and start the application and database
docker-compose up --build
```

**Access Points:**
- 🌐 **API URL:** `http://localhost:8080`
- 🗄️ **Database:** PostgreSQL (Internal Docker Network)

---

### 💻 3. Run Locally (with H2 Database)

For quick development and testing without Docker:

```bash
./mvnw spring-boot:run
```

**Access Points:**
- 🌐 **API URL:** `http://localhost:8080`
- 🗄️ **H2 Console:** `http://localhost:8080/h2-console`
  - **JDBC URL:** `jdbc:h2:mem:gymdb`
  - **Username:** `sa`
  - **Password:** _(empty)_

---

## 🧪 How to Run Tests

### ✅ Unit & Integration Tests

Run all JUnit tests (including TDD, Mocking, and Property-Based tests):

```bash
./mvnw test
```

---

### 📊 Coverage Report (JaCoCo)

Generate the code coverage report:

```bash
./mvnw clean verify
```

**📁 Report Location:** `target/site/jacoco/index.html`

---

### 🧬 Mutation Testing (PITest)

Run mutation tests to verify test quality:

```bash
./mvnw org.pitest:pitest-maven:mutationCoverage
```

**📁 Report Location:** `target/pit-reports/index.html`

---

### 🚀 Performance Tests (k6)

Run load tests (requires k6 installed):

```bash
k6 run tests/k6/load_test.js
```

---

### 🔄 API Automation (Newman)

To run the Postman collection from the command line:

```bash
newman run tests/postman/CEN315_Gym.postman_collection.json
```

> ⚠️ **Note:** Make sure the application is running before executing this command.

---

## 📚 Documentation

- 📄 **Test Strategy Plan:** `docs/Test Strategy Plan.pdf`
- 🔒 **Security Report:** `docs/security_report.html`
- 📊 **Code Coverage:** `target/site/jacoco/index.html`
- 🧬 **Mutation Report:** `target/pit-reports/index.html`

---

## 🛠️ Tech Stack

| Category | Technologies |
|----------|-------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.5.8 |
| **Database** | PostgreSQL (Production), H2 (Testing) |
| **Testing** | JUnit 5, Mockito, jqwik, k6, Newman |
| **Build Tool** | Maven |
| **Containerization** | Docker, Docker Compose |
| **Code Quality** | JaCoCo, PITest, OWASP ZAP |

---

## 📝 License

This project is developed as part of CEN315 Introduction to Test Engineering course.

**© 2025 Mehmet Kaplan - All Rights Reserved**

