# 📊 ClusteredDataWarehouse

A robust FX deals ingestion service designed as part of a technical assessment for ProgressSoft Corporation. This service validates, processes, and persists Foreign Exchange (FX) deal data into a PostgreSQL database, ensuring data quality, deduplication, and high observability.

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Docker](https://img.shields.io/badge/Containerized-Yes-blue)
![Test Coverage](https://img.shields.io/badge/Coverage-80%25-brightgreen)

---

## 📚 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [API Documentation](#api-documentation)
- [Validation Rules](#validation-rules)
- [Project Structure](#project-structure)
- [Running the App](#running-the-app)
- [Testing](#testing)
- [Makefile Commands](#makefile-commands)
- [Contact](#contact)

---

## 🧩 Overview

This service is a part of a clustered data warehouse for FX trading platforms. It accepts and validates deal data, prevents duplicate insertions based on deal ID, and ensures persistence without rollback even in case of partial failures.

---

## ✅ Features

- ✅ Accept FX deals via REST API
- ✅ Validation using Jakarta Bean Validation
- ✅ Duplicate ID detection
- ✅ No rollback policy — valid rows are always saved
- ✅ Global exception handling
- ✅ Logging with AspectJ AOP
- ✅ Dockerized deployment
- ✅ Unit tests with 80%+ coverage

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.2.x
- PostgreSQL 16
- Maven
- Docker & Docker Compose
- JUnit 5 & Mockito
- ModelMapper
- SLF4J / Logback

---

## 📬 API Documentation

### Endpoint: `POST /api/deals/import`

**Description:**  
Bulk imports FX deal records. Each request contains a list of deals.

### Sample Request:

```json
[
  {
    "id": "FX10001",
    "fromCurrency": "USD",
    "toCurrency": "EUR",
    "timestamp": "2024-01-01T12:30:00",
    "amount": 15000.75
  },
  {
    "id": "FX10002",
    "fromCurrency": "GBP",
    "toCurrency": "JPY",
    "timestamp": "2024-01-01T13:00:00",
    "amount": 25000.00
  }
]
