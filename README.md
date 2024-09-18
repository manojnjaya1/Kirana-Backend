# Kirana Store Backend Service

This backend service is built for Kirana stores to manage their daily transactions, authenticate users, and generate financial reports. It supports features like JWT authentication, role-based access control, and rate-limited API requests.

## Features

- **JWT Authentication**
- **Role-based Access Control**: User/ADMIN
- **Transaction Management**: Record credits and debits
- **Currency Conversion**: Convert transaction amounts between currencies
- **Reports**: Generate weekly, monthly, and yearly reports
- **Rate Limiting**: Limit transaction creation to 10 requests per minute
- **Concurrency Control**: Safe transaction recording with concurrency control

## Tech Stack

- **Java**: JDK 17
- **Spring Boot**: Version 3.x
- **MongoDB**: For storing transactions and user data
- **Spring Security**: For authentication and authorization
- **JWT**: JSON Web Token for securing APIs
- **Lombok**: For reducing boilerplate code
- **Bucket4j**: For rate-limiting

## Getting Started

### Prerequisites

- Java 17+
- MongoDB installed and running
- Maven 3.6+
- IDE (e.g., IntelliJ, Eclipse)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/kirana-store-backend.git
   cd kirana-store-backend
2. Install Dependencies:
   ```bash
   mvn clea install
3. Configure the application.yml or application.properties file for MongoDB and JWT configuration.
4. Run Application:
   ```bash
   mvn spring-boot:run

## API Documentation

### 1. User Registration

- **Endpoint:** `/api/auth/register`
- **Method:** `POST`
- **Description:** Register a new user.

**Request:**
```json
{
  "username": "john_doe",
  "password": "securepassword",
  "role": "ROLE_USER"
}
```
**Response:**
```json
{
  "username": "john_doe"
}
```

### 2. User Login

- **Endpoint:** `/api/auth/login`
- **Method:** `POST`
- **Description:** Authenticate user and receive a JWT token.

**Request:**
```json
{
  "username": "john_doe",
  "password": "securepassword"
}
```
**Response:**
```json
{
  "username": "john_doe",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
### 3. Create Transaction

- **Endpoint:** `/api/transactions`
- **Method:** `POST`
- **Description:** Record a credit or debit transaction.

**Request:**
```json
{
  "amount": 500.0,
  "type": "credit",
  "currency": "INR",
  "convertedAmount": 6.5
}
```

**Response:**
```json
{
  "id": "645a9f6e8c9e4e56d2e2a034",
  "amount": 500.0,
  "type": "credit",
  "currency": "INR",
  "convertedAmount": 6.5,
  "timestamp": "2023-09-12T14:20:30"
}
```

#### Rate Limiting: Maximum of 10 requests per minute.
### 4. Get Financial Report

- **Endpoint:** `/api/reports/{period}`
- **Method:** `GET`
- **Description:** Fetch financial report for a specific period (weekly, monthly, yearly).

**Request:** None

**Response:**
```json
{
  "totalCredits": 5000.0,
  "totalDebits": 2000.0,
  "netFlow": 3000.0,
  "period": "monthly"
}
```

### 5. User Role-based Endpoints

- **ADMIN:** Can access all transactions and reports.
- **USER:** Can only access their transactions and reports.

### Rate Limiting

The Transactions API is rate-limited to 10 requests per minute using Bucket4J.

### Security

**JWT Authentication:** All secured endpoints require the Authorization header with a valid JWT token.

**Example:**
```bash
Authorization: Bearer <JWT_TOKEN>
```

## Models

### 1. User Model

```java
@Document(collection = "users")
public class Users {
    @Id
    private String username;
    private String password;
    private String role;
}
```

### 2. Transaction Model

```java
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private Double amount;
    private String type;  // credit or debit
    private String currency;
    private Double convertedAmount;
    private LocalDateTime timestamp;
}
```
### 3. Report Model

```java
@Data
@AllArgsConstructor
public class Report {
    private double totalCredits;
    private double totalDebits;
    private double netFlow;
    private String period;
}
```

## Project Structure

```bash
src
├── main
│   ├── java
│   │   └── com.example.Kirana
│   │       ├── controllers      # REST Controllers
│   │       ├── dto              # Data Transfer Objects
│   │       ├── models           # MongoDB Models
│   │       ├── repository       # Repository Interfaces
│   │       ├── services         # Service Interfaces and Implementations
│   │       ├── jwtConfig        # JWT Configuration
│   │       └── config           # App Configuration (Security, Rate Limiting)
│   └── resources
│       └── application.yml       # Application Configuration


```
## Contact

- Author: [Manoj](mailto:manojnjaya@gmail.com)
- GitHub: [manojnjaya1](https://github.com/manojnjaya1)
