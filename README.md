# 📦 Small ERP System

A Spring Boot–based monolithic ERP solution tailored for small businesses, integrating core modules such as **Accounts**, **Inventory**, **Sales**, and **Purchases** to streamline day-to-day operations.

---

## 🧱 Table of Contents

1. [Features](#features)
2. [Technology Stack](#technology-stack)
3. [Getting Started](#getting-started)
4. [Configuration](#configuration)
5. [Database & Migrations](#database--migrations)
6. [Business Logic Summary](#business-logic-summary)
7. [Testing](#testing)
8. [Deployment](#deployment)
9. [Contributing](#contributing)
10. [License](#license)

---

## ✅ Features

## ✅ Features

### 🧾 Accounting
- Chart of accounts management
- Journal entries and ledger tracking
- Income, expense, and profit/loss reports
- Customer and supplier balance tracking

### 📦 Inventory Management
- Item master data with category and unit support
- Inventory movement: receipts, issues, transfers
- Warehouse-level stock tracking
- Stock availability and valuation reports

### 🛒 Sales Management
- Sales order and invoice processing
- Customer management and pricing rules
- Sales history and performance reports
- Invoice due tracking

### 📥 Purchase Management
- Purchase order and invoice handling
- Supplier management
- Purchase return and GRN tracking
- Payment due tracking

### 📊 Reporting & Dashboard
- Real-time business overview dashboard
- Inventory status, financial summary, and sales trends
- Export to PDF/Excel

### 🔐 User & Role Management
- User authentication and authorization
- Role-based access control (RBAC)
- Audit logging for critical actions

### ⚙️ System Utilities
- Configurable company settings (fiscal year, logo, currency)
- Master data import/export support
- Multi-language (i18n) ready (optional)


---

## 💻 Technology Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL / MySQL
- Redis (optional)
- Flyway (for DB migration)
- Docker & Docker Compose (optional)

---

## 🚀 Getting Started

### Prerequisites

- Java 17
- Maven 3.4+
- PostgreSQL or MySQL running
- (Optional) Redis

### Running Locally

```bash
git clone https://github.com/your-org/inventory-api.git
cd inventory-api
cp .env.sample .env
./mvnw spring-boot:run
