
# 🏋️‍♂️ Gym Service: End-to-End Test Engineering Project

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.8-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)](https://www.docker.com/)
[![Test Coverage](https://img.shields.io/badge/Coverage-90%25+-success.svg)](https://www.jacoco.org/)

> A high-reliability RESTful API developed for **CEN315 Introduction to Test Engineering** course, implementing a complete test engineering lifecycle from TDD to advanced quality assurance practices.

## 📖 Project Overview

**Gym Service** is a comprehensive fitness center management system that handles:

- 👥 **Member Management** - Registration, profile management, and membership tiers
- 📅 **Class Scheduling** - Session planning and capacity management
- 💰 **Dynamic Pricing Engine** - Intelligent pricing based on demand and membership type
- 🎫 **Reservation System** - Real-time booking with validation

### ✨ Key Technical Features

* **Dynamic Pricing Algorithm**
  - Surge Pricing when occupancy exceeds 80%
  - Tier-based discounts (Premium/Student memberships)
  - Real-time price calculation based on demand

* **Design by Contract (Formal Verification)**
  - Enforces system invariants using Spring Assertions
  - Formal `validateState()` methods for business logic integrity
  - Defensive programming with comprehensive validation
  
  **Example Invariants:**
  ```java
  // Precondition: capacity > 0 && occupancy >= 0
  // Postcondition: calculatedPrice >= basePrice
  // Invariant: occupancy <= capacity
  
  public void createReservation(Member member, GymClass gymClass) {
      assert member != null && gymClass != null;
      assert gymClass.getAvailableCapacity() > 0;
      
      // Business logic...
      
      assert reservation.getPrice() >= gymClass.getBasePrice();
      validateState(); // Verify all invariants hold
  }
  ```

* **Enterprise-Grade Quality Assurance**
  - Mutation Testing (PITest)
  - Property-Based Testing (jqwik)
  - Combinatorial Testing (ACTS)
  - Performance Testing (k6)
  - Security Testing (OWASP ZAP)
  - API Contract Testing (Newman)

## 🛠️ Tech Stack & Requirements

### Core Technologies

* **Language:** Java 21 (Temurin JDK)
* **Framework:** Spring Boot 3.5.8
* **Database:** H2 (In-Memory for Dev) / PostgreSQL 15 (Docker for Prod)
* **Build Tool:** Maven 3.9+
* **Containerization:** Docker & Docker Compose

### Testing Tools & Libraries

| Tool | Purpose | Version |
|------|---------|---------|
| JUnit 5 | Unit Testing Framework | 5.10.1 |
| Mockito | Mocking Framework | 5.8.0 |
| jqwik | Property-Based Testing | 1.8.2 |
| PITest | Mutation Testing | 1.15.3 |
| Newman | API Testing | Latest |
| k6 | Load/Performance Testing | Latest |
| OWASP ZAP | Security Testing | 2.14+ |
| JaCoCo | Code Coverage | 0.8.11 |

---

## 🚀 Getting Started

### Prerequisites

Before running the project, ensure you have the following installed:

- **Java 21** or higher ([Download](https://adoptium.net/))
- **Docker & Docker Compose** ([Download](https://www.docker.com/products/docker-desktop))
- **Maven 3.9+** (included via wrapper: `./mvnw`)

### Installation & Execution

#### Option 1: Run with Docker (🌟 Recommended)

The project is fully containerized with a multi-stage build for the application and a dedicated PostgreSQL instance.

```bash
# Build and start all services
docker-compose up --build
```

**Services:**
- 🌐 **API:** http://localhost:8080
- 🗄️ **Database:** PostgreSQL (Internal network only)
- 📊 **Health Check:** http://localhost:8080/actuator/health

**API Endpoints:**
- `GET /members` - List all members
- `GET /members/{id}` - List member by ID
- `POST /members` - Register new member
- `GET /classes` - List available classes
- `POST /classes` - Create new class
- `POST /reservations` - Create reservation
- `DELETE /reservations/{id}` - Cancel reservation

#### Option 2: Run Locally (Development Mode)

```bash
# Run with embedded H2 database
./mvnw spring-boot:run
```

**Development Resources:**
- 🌐 **API:** http://localhost:8080
- 💾 **H2 Console:** http://localhost:8080/h2-console
  - **JDBC URL:** `jdbc:h2:mem:gymdb`
  - **Username:** `sa`
  - **Password:** _(empty)_

#### Stopping the Application

```bash
# For Docker
docker-compose down

# For local run (Ctrl+C in terminal)
```
---

## 🧪 Testing Suite Execution

This project implements a comprehensive testing strategy covering multiple testing levels and methodologies.

### 📊 Unit & Integration Tests

Run all unit and integration tests with JaCoCo code coverage:

```bash
# Run all tests and generate coverage report
./mvnw clean verify
```

**📈 Coverage Report:** Open `target/site/jacoco/index.html` in your browser

**Test Types Included:**
- ✅ Unit Tests (JUnit 5 + Mockito)
- ✅ Property-Based Tests (jqwik)
- ✅ Combinatorial Tests (ACTS)
- ✅ Integration Tests (Spring Boot Test)

### 🧬 Mutation Testing

Evaluate the quality and effectiveness of your test suite:

```bash
# Run PITest mutation analysis
./mvnw org.pitest:pitest-maven:mutationCoverage
```

**📊 Mutation Report:** Open `target/pit-reports/index.html` in your browser

**Metrics:**
- Line Coverage
- Mutation Coverage
- Test Strength

### 🌐 API Contract Testing

Test API endpoints with comprehensive assertions:

```bash
# Install Newman globally (if not installed)
npm install -g newman

# Run Postman collection
newman run tests/postman/collection.json
```

**Tests Include:**
- Request/Response validation
- Status code verification
- Business logic assertions

### ⚡ Performance & Load Testing

Measure system performance under load:

```bash
# Install k6 (if not installed)
# Windows: choco install k6
# macOS: brew install k6

# Run load tests
k6 run tests/k6/load_test.js
```

**Metrics Monitored:**
- Response times (p95, p99)
- Requests per second
- Error rates
- Throughput

### 🔒 Security Testing

Run security vulnerability scanning with OWASP ZAP:

```bash
# Results available at: docs/security_report.html
```

### 🔥 Chaos Engineering & Resilience Testing

Test the system's resilience against infrastructure failures:

#### Database Failure Simulation

```bash
# 1. Start the system with Docker
docker-compose up -d

# 2. Simulate database crash
docker stop gym-postgres

# 3. Make API requests - observe graceful degradation
curl http://localhost:8080/api/members
```

**Expected Behavior:**
- ✅ Returns `503 Service Unavailable` instead of `500 Internal Server Error`
- ✅ No stack traces exposed to clients (security improvement)
- ✅ User-friendly JSON error message
- ✅ System remains stable (no cascading failures)

**Implementation Details:**
- Global `@ControllerAdvice` catches `DataAccessException`
- Converts JDBC exceptions → HTTP 503 responses
- Prevents information disclosure vulnerabilities

**Verification:**

```bash
# ❌ Response before mitigation:
HTTP 500 Internal Server Error
{
  "error": "org.springframework.jdbc.CannotGetJdbcConnectionException: ..."
  // Stack trace visible to client
}

# ✅ Response after mitigation:
HTTP 503 Service Unavailable
{
  "error": "Service temporarily unavailable",
  "message": "Database connection failed. Please try again later."
}
```

**Resilience Patterns Implemented:**
- 🛡️ **Graceful Degradation** - System fails safely without crashing
- 🔒 **Information Hiding** - No internal details leaked to clients
- 📊 **Proper HTTP Status Codes** - 503 indicates temporary unavailability
- 🔄 **Fail-Fast Behavior** - Quick error responses instead of timeouts

**Restore Database:**
```bash
docker-compose start gym-postgres
```

For detailed chaos engineering analysis and results, see `docs/Project_Report.pdf` Section 5.13.

### 📋 Run All Tests at Once

```bash
# Complete test suite execution
./mvnw clean verify && \
./mvnw org.pitest:pitest-maven:mutationCoverage && \
newman run tests/postman/collection.json && \
k6 run tests/k6/load_test.js
```

---

## 📊 CI/CD & Quality Dashboard

The project utilizes **GitHub Actions** for continuous quality assurance. Every commit triggers a comprehensive pipeline that generates a **Dynamic Quality Dashboard** with real-time metrics.

### Pipeline Stages

1. **🏗️ Build & Compile** - Maven clean install
2. **🧪 Unit Tests** - JUnit 5 + jqwik property tests
3. **📊 Coverage Analysis** - JaCoCo report generation
4. **🧬 Mutation Testing** - PITest mutation coverage
5. **🌐 API Tests** - Newman contract validation
6. **⚡ Load Tests** - k6 performance benchmarks
7. **🔒 Security Scan** - OWASP ZAP vulnerability assessment

### Quality Metrics Dashboard

Every build generates a summary including:

* **JaCoCo Summary:** Package-level coverage statistics
* **PITest Summary:** Mutation coverage and test strength percentages
* **Newman Results:** API assertion status matrix
* **k6 Metrics:** Latency and threshold validations

---

## 📂 Documentation Structure

* `docs/Test_Strategy_Plan.pdf` - Comprehensive test planning and strategy document
* `docs/Project_Report.pdf` - Final project report with analysis and conclusions
* `docs/security_report.html` - OWASP ZAP security analysis report
* `tests/postman/` - Newman/Postman API test collections
* `tests/k6/` - Performance and load testing scripts
* `tools/` - ACTS combinatorial testing tools

---

## 🏆 Quality Metrics

| Metric | Target | Real Status | Tool |
|--------|--------|-------------|------|
| **Instruction Coverage** | >80% | ✅ **91%** | JaCoCo |
| **Mutation Coverage** | >80% | ✅ **83%** | PITest |
| **Test Strength** | >90% | ✅ **92%** | PITest |
| **Property Tests** | 1000 cases | ✅ **1000/1000** | jqwik |
| **API Assertions** | 100% pass | ✅ **100%** | Newman |
| **Performance (p95)** | <2000ms | ✅ **6.47ms** | k6 |
| **Security** | OWASP verified | ✅ **Validated** | ZAP |
| **Chaos Resilience** | Graceful degradation | ✅ **503 Response** | Docker |

---

