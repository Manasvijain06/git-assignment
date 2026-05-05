# 🧾 Reimbursement Portal

---

# 1. Introduction

## 1.1 Purpose

The Reimbursement Portal is a backend-driven enterprise application that enables employees to submit reimbursement claims and allows managers/admins to review, approve, or reject them.

It demonstrates:

* REST API design using Spring Boot
* Role-based access control
* Layered architecture (Controller → Service → Repository)
* Data persistence using JPA/Hibernate
* Validation, logging, and exception handling

---

## 1.2 Scope

The system supports:

* User management (Admin, Manager, Employee)
* Claim submission
* Claim approval/rejection workflow
* Claim tracking

---

## 1.3 Technologies
```
| Layer             | Technology                       | Purpose                              |
| ----------------- | -------------------------------- | ------------------------------------ |
| Backend Language  | Java 17                          | Primary backend development language |
| Backend Framework | Spring Boot                      | REST API development                 |
| Web Layer         | Spring Web MVC                   | Request handling & controllers       |
| Security          | Spring Security                  | Authentication & authorization       |
| Password Security | BCrypt                           | Password hashing                     |
| Persistence       | Spring Data JPA                  | ORM abstraction                      |
| ORM               | Hibernate                        | Entity mapping                       |
| Database          | PostgreSQL                       | Primary relational database          |
| Frontend          | HTML, CSS, JavaScript            | UI dashboard                         |
| Build Tool        | Maven                            | Dependency & build management        |
| Testing           | JUnit, Mockito, Spring Boot Test | Unit & integration testing           |
| Coverage          | JaCoCo                           | Code coverage reporting              |
| API Testing       | Postman                          | API validation                       |
```
---

# 2. System Overview

## 2.1 System WorkFlow

1. Admin creates users
2. Admin assigns manager to employees
3. Employee submits claim
4. System routes claim to manager/admin
5. Manager/Admin reviews claim
6. Status updated
7. Employee tracks claim status
   
```
Employee → Submit Claim → System Routes → Manager/Admin Review → Approve/Reject → Employee Views Status
```


---

## 2.2 User Roles & Responsibilities
```
| Role     | Responsibilities                                 |
| -------- | ------------------------------------------------ |
| Admin    | Manage users, assign managers, fallback approver |
| Manager  | Review assigned claims, approve/reject claims    |
| Employee | Submit claims, track status                      |
```
---

## 2.3 Key Objectives

* Automate reimbursement workflow
* Ensure role-based access control
* Maintain data consistency
* Provide clean REST APIs
* Enable scalable claim tracking system

---

# 3. 🏗 System Architecture

```
Frontend (HTML/JS/CSS)
        ↓
Controller Layer (REST APIs)
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (JPA)
        ↓
Database (PostgreSQL)
```

---

# 4. 🗄 Database Design

## 4.1 Tables

### 👤 User Table
```
| Column     | Type   | Description                |
| ---------- | ------ | -------------------------- |
| id         | Long   | Primary Key                |
| name       | String | User name                  |
| email      | String | Unique email               |
| password   | String | Encrypted password         |
| role       | ENUM   | ADMIN / MANAGER / EMPLOYEE |
| manager_id | Long   | Reference                  |
```
---

### 📄 Claim Table

```
| Column      | Type   | Description                     |
| ----------- | ------ | ------------------------------- |
| id          | Long   | Primary Key                     |
| employee_id | Long   | FK → User                       |
| manager_id  | Long   | FK → User                       |
| amount      | Double | Claim amount                    |
| description | String | Claim details                   |
| status      | ENUM   | SUBMITTED / APPROVED / REJECTED |
| comment     | String | Manager/Admin comment           |
| created_at  | Date   | Submission date                 |
```
---

## 4.2 Relationship Mapping

* User (1) → (Many) Claims
* Manager (User) → Employees (User)
* Claim → Employee (Many-to-One)
* Claim → Manager (Many-to-One)

---

# 5. API STRUCTURE

## 5.1 Auth APIs
```
| Method | Endpoint        | Description |
| ------ | --------------- | ----------- |
| POST   | /api/auth/login | User login  |
```
---
## 5.2 User APIs
```
| Method | Endpoint             | Description             |
| ------ | -------------------- | ----------------------- |
| POST   | /api/users           | Create new user (Admin) |
| GET    | /api/users/{id}      | Get user by ID          |
| GET    | /api/users           | Get all users           |
| PUT    | /api/users/{id}      | Update user details     |
| DELETE | /api/users/{id}      | Delete user             |
| GET    | /api/users/managers  | Get all managers        |
| GET    | /api/users/employees | Get all employees       |
```
---
## 5.3 Claim APIs

```
| Method | Endpoint                         | Description                |
| ------ | -------------------------------- | -------------------------- |
| POST   | /api/claims                      | Create claim               |
| GET    | /api/claims/{id}                 | Get claim by ID            |
| GET    | /api/claims                      | Get all claims (paginated) |
| GET    | /api/claims/employee/{id}        | Employee claims            |
| GET    | /api/claims/pending              | Pending claims             |
| GET    | /api/claims/manager/{id}         | Manager claims             |
| GET    | /api/claims/manager/{id}/pending | Manager pending            |
| GET    | /api/claims/admin/pending        | Admin pending claims       |
| PUT    | /api/claims/{id}/{action}        | Approve/Reject             |
```
---

# 6. 🧩 DTO MAPPING

## Request DTOs

* LoginRequest → email, password
* ClaimRequest → employeeId, amount, description, date
* ClaimActionRequest → comment

## Response DTOs

* UserResponse → user details + token
* ClaimResponse → claim details + status
* ApiResponse<T> → standard wrapper

---

# 7. Repository & Service Interaction

```
Controller → Service → Repository → DB
```

### Flow Example (Claim Creation)

1. Controller receives request
2. Service validates business logic
3. Repository saves entity
4. Response mapped via DTO

---

# 8. Frontend Structure

```
frontend
│
├── css
│   ├── style.css
│   └── style1.css
│
├── html
│   ├── admin.html
│   ├── assign-manager.html
│   ├── employee.html
│   ├── index.html
│   ├── manager.html
│   ├── profile.html
│   ├── review-claims.html
│   └── user.html
│
└── js
    ├── admin.js
    ├── assign-manager.js
    ├── auth.js
    ├── employee.js
    ├── manager.js
    ├── profile.js
    ├── review-claims.js
    └── user.js
```

---

# 9. 📁 Backend Structure

```
backend/reimbursement
    │
    └── src
        │
        └── main
            │
            └── java/com/manasvi/reimbursement
                │
                ├── controller
                │
                ├── dto
                │   ├── Request
                │   │   ├── ClaimActionRequest.java
                │   │   ├── ClaimRequest.java
                │   │   ├── LoginRequest.java
                │   │   └── UserRequest.java
                │   │
                │   └── Response
                │       ├── ApiResponse.java
                │       ├── ClaimResponse.java
                │       └── UserResponse.java
                │
                ├── entity
                │   ├── Claim.java
                │   └── User.java
                │
                ├── enums
                │   ├── ClaimStatus.java
                │   └── Role.java
                │
                ├── exception
                │   ├── DuplicateResourceException.java
                │   ├── GlobalExceptionHandler.java
                │   ├── ResourceNotFoundException.java
                │   └── ValidationException.java
                │
                ├── mapper
                │   ├── ClaimMapper.java
                │   └── UserMapper.java
                │
                ├── repository
                │   ├── ClaimRepository.java
                │   └── UserRepository.java
                │
                ├── service
                │   ├── ClaimService.java
                │   └── UserService.java
                │
                └── ReimbursementApplication.java
│
├── resources
├── test
├── target
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
```
---
## Sample API Request/Response

Example:

(POST)--> URL: http://localhost:8080/api/claims

Request:
```
{
  "employeeId": 1,
  "amount": 1500,
  "description": "Travel reimbursement"
}
```

Response:
```
{
  "success": true,
  "message": "Claim submitted successfully",
  "data": {
    "id": 10,
    "status": "SUBMITTED"
  }
}
```
---
#10. Exception Handling

- GlobalExceptionHandler handles all exceptions centrally
- Custom exceptions:
  - ResourceNotFoundException
  - DuplicateResourceException
  - ValidationException

Standard API error format:
```
{
  "timestamp": "",
  "message": "",
  "status": 400
}
```
---

# 11. Testing Strategy

* JUnit for unit testing
* Mockito for mocking dependencies
* Spring Boot Test for integration tests
* Target: **70%+ JaCoCo coverage**(Achieved)
```
Run command:
  mvn clean test
  mvn jacoco:report
```
---

# 12. Code Quality

* Checkstyle enforced
* Clean code principles
* Proper Java naming conventions
* No unused imports/variables
* Added Java Doc for readability

---

# 13. Features

## User Authentication
- Secure login system for all users (Admin, Manager, Employee)
- Credentials are validated against stored user data in the database
- Passwords are encrypted using BCrypt hashing
- Only authenticated users can access protected endpoints
---
## Role-Based Access Control (RBAC)
* System is divided into three main roles:
  - Admin
  - Manager
  - Employee
* Each role has specific permissions and restricted access:
  - Admin: Full system control (user management, oversight)
  - Manager: Review and process claims assigned to them
  - Employee: Submit and track their own claims
  -Access to APIs is controlled using Spring Security role validation
---
##  Claim Submission
  - Employees can create reimbursement claims through a simple API request
  - Required details include: Claim amount, Date(optional), Description of expense
  - Each claim is automatically linked to the logged-in employee
  - Claims are initially saved with SUBMITTED status
  - System ensures validation to prevent invalid or duplicate entries
---
## Pagination Support
  - Large datasets (users/claims) are handled using pagination
  - Improves system performance and reduces load time
  - Supports: Page number ,Page size
  - Supports page number and page size parameters to efficiently handle large datasets and reduce backend load, making the system scalable for enterprise-level usage.
---
## Logging System
  - Application uses structured logging (SLF4J / Logback)
  - Logs important system events such as:
    1) User login attempts
    2) Claim creation and updates
    3) Approval/rejection actions
    4) Exceptions and errors
  - Helps in debugging and monitoring system behavior
  - Logs are categorized into INFO, WARN, and ERROR levels
---
## Standardized API Response Format 
 All APIs return a consistent response structure:
  - Success status
  - Message
  - Data payload

---
# 14. Security Implementation

- Spring Security is used for authentication & authorization
- Passwords are encrypted using BCrypt
- Role-based access control (RBAC) implemented
- Endpoints are protected based on roles:
  - ADMIN → full access
  - MANAGER → approve/reject claims
  - EMPLOYEE → submit & view claims


---

# 15. 📌 Conclusion

The Reimbursement Portal provides a scalable backend system demonstrating enterprise-level Spring Boot architecture with proper workflow, security, and modular design.
