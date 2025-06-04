# 🌐 FX Deals Data Warehouse

A **high-performance** FX deals data warehouse system built for **Bloomberg-style ingestion and validation**, enabling robust persistence of foreign exchange transactions with precision, reliability, and auditability.

![Status](https://img.shields.io/badge/Status-Production--Ready-green)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Coverage](https://img.shields.io/badge/Coverage-80%25-brightgreen)

## 📚 Table of Contents

- [📌 Project Summary](#-project-summary)
- [🚀 Features](#-features)
- [🧰 Technology Stack](#-technology-stack)
- [⚙️ Getting Started](#-getting-started)
- [📬 API Specification](#-api-specification)
- [📁 Folder Structure](#-folder-structure)
- [📸 Code Coverage Report](#-code-coverage-report)

## 📌 Project Summary

**FX Deals Warehouse** is a backend system designed to process, validate, and persist foreign exchange deal records. The solution enforces strong validation rules (e.g., ISO 4217 currency codes, unique deal IDs, and amount checks) and ensures that all valid deals are saved—even when some records fail—thanks to its **no rollback** strategy.

## 🚀 Features

✅ ISO 4217 Currency Code Verification  
✅ Unique Deal ID Enforcement  
✅ Strict Timestamp & Amount Validation  
✅ Rejection of Duplicates with Informative Logs  
✅ Fault-Tolerant Partial Save (no rollback on batch errors)  
✅ Clean and Audit-Friendly Logging  
✅ Containerized Deployment with Makefile Commands

## 🧰 Technology Stack

### 🔧 Backend
- **Java 21**
- **Spring Boot 3.2.0**
- **PostgreSQL 16**

### 🧪 Testing & Utilities
- **Maven**
- **JUnit 5 + AssertJ + Mockito**
- **SLF4J / Logback for structured logging**

### 📦 Containerization
- **Docker**
- **Docker Compose**
- **Makefile automation**

## ⚙️ Getting Started

### Prerequisites

Make sure you have the following installed:

- Java 21 (JDK)
- Docker + Docker Compose
- Maven 3.8+

### Installation & Run

```bash
# Clone the repository
git clone https://github.com/AymaneTech/progres-soft-technical-test
cd progres-soft-technical-test
```

Start services using Makefile:

```bash
make up       # Start backend + database containers
make down     # Stop all services
make test     # Run all unit & integration tests
make clean    # Remove build files
```

## 📬 API Specification

### 📤 Import FX Deal

**POST** `/api/v1/deals`  
**Content-Type:** `application/json`

#### 🔽 Request Body
```json
{
  "id": "DR123456",
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "timestamp": "2024-01-01T10:00:00Z",
  "amount": 1000000.00
}
```

#### 📤 Response
```json
{
  "id": "DR123456",
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "timestamp": "2024-01-01T10:00:00Z",
  "amount": 1000000.00
}
```

In case of invalid data (e.g., duplicate ID or wrong currency format), the response includes error details while still persisting valid records in the same batch.

## 📁 Folder Structure

```
clustereddatawarehouse/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/progressoft/clustereddatawarehouse/
│   │   │       ├── aspect/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── exception/
│   │   │       └── model/
│   │   │       └── repositorie/
│   │   │       └── service/
│   │   └── resources/             # Application configs
│   └── test/                      # Unit and integration tests
├── Dockerfile
├── docker-compose.yml
└── Makefile
```

## 📸 Code Coverage Report

After running tests (`make test`), a detailed test coverage report is generated. Here's a sample screenshot:

> 📷 **Example Coverage Report**

![Coverage Screenshot](https://github.com/user-attachments/assets/4ef655ab-8d7a-4c10-a589-bb141558f85d)


The current codebase has **~80% test coverage**, focusing on:
- ✅ Validation logic  
- ✅ Deal processing service  
- ✅ Controller endpoint behavior

You can find the full HTML coverage report at:
```
/target/site/jacoco/index.html
```
