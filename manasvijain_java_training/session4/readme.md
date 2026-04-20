# Spring Advance - TODO Application

## 🚀 Overview

This is a Spring Boot-based TODO Management Application developed as part of the Enterprise Flow & Testing Assignment (Session 4 & Session 5).
The project demonstrates key backend concepts such as **REST APIs, layered architecture, JPA (Hibernate), DTO pattern, validation, and exception handling**.

The project demonstrates key backend concepts such as:
REST API Development
Layered Architecture
JPA (Hibernate)
DTO Pattern
Validation
Exception Handling
Logging
Unit Testing (JUnit + Mockito)
External Service Simulation

---

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA (Hibernate)
* PostgreSQL
* Maven
* JUnit & Mockito
* Postman / Thunder Client

---

## 🏗️ Project Architecture

The application follows a **strict layered architecture**:

```id="arch1"
Controller → Service → Repository → Database
```

---

## 📂 Project Structure

```
java/session4/SpringAdvance
 └── src/main/java/com/Nucleusteq/SpringAdvance
      ├── controller
      │     └── TodoController.java
      │
      ├── service
      │     └── TodoService.java
      |     └── NotificationServiceClient.java   <-- NEW
      │
      ├── repository
      │     └── TodoRepository.java
      │
      ├── entity
      │     └── Todo.java
      │
      ├── dto
      │     ├── TodoRequestDTO.java
      │     └── TodoResponseDTO.java
      │
      ├── exception
      │     ├── TodoNotFoundException.java
      │     ├── InvalidStatusTransitionException.java
      │     └── GlobalExceptionHandler.java
```

---

## 📌 Features (Assignment 4)
Core Features
Create TODO
Get all TODOs
Get TODO by ID
Update TODO
Delete TODO

Advanced Features (Assignment 5)
 SLF4J Logging (Controller + Service)
 Unit Testing using JUnit & Mockito
 Dummy External Service (NotificationServiceClient)
 DTO-based architecture
 Validation using @Valid
 Global Exception Handling
 Status transition rules enforced

---

## Entity Structure

### 📦 Todo

| Field       | Type      | Description         |
| ----------- | --------- | ------------------- |
| id          | Long      | Primary Key         |
| title       | String    | Task title          |
| description | String    | Task details        |
| status      | Enum      | PENDING / COMPLETED |
| createdAt   | Timestamp | Auto-generated      |

---

## DTO Validation

### TodoRequestDTO

* `@NotNull` → title is required
* `@Size(min = 3)` → title must be at least 3 characters

---

## API Endpoints

### 🔹 1. Create TODO

```
POST /todos
```
![create](https://github.com/Manasvijain06/Assignments/blob/dd7dab1b861d6f34d410b32e788e47955a5a5662/manasvijain_java_training/session4/SpringAdvance/Screenshot/Create.png)

Default status
![status](https://github.com/Manasvijain06/Assignments/blob/dd7dab1b861d6f34d410b32e788e47955a5a5662/manasvijain_java_training/session4/SpringAdvance/Screenshot/DefaultStatus.png)

Invalid Status
![invalid](https://github.com/Manasvijain06/Assignments/blob/dd7dab1b861d6f34d410b32e788e47955a5a5662/manasvijain_java_training/session4/SpringAdvance/Screenshot/invalidStatus.png)

---

### 🔹 2. Get All TODOs

```
GET /todos
```
![Get All](https://github.com/Manasvijain06/Assignments/blob/dd7dab1b861d6f34d410b32e788e47955a5a5662/manasvijain_java_training/session4/SpringAdvance/Screenshot/getAll.png)

---

### 🔹 3. Get TODO by ID

```
GET /todos/{id}
```
![Get by id](https://github.com/Manasvijain06/Assignments/blob/dd7dab1b861d6f34d410b32e788e47955a5a5662/manasvijain_java_training/session4/SpringAdvance/Screenshot/GetById.png)

---

### 🔹 4. Update TODO

```
PUT /todos/{id}
```
![put](https://github.com/Manasvijain06/Assignments/blob/dd7dab1b861d6f34d410b32e788e47955a5a5662/manasvijain_java_training/session4/SpringAdvance/Screenshot/update.png)

Update Not Found

![NotFound](https://github.com/Manasvijain06/Assignments/blob/dd7dab1b861d6f34d410b32e788e47955a5a5662/manasvijain_java_training/session4/SpringAdvance/Screenshot/UpdateNotFound.png)

---
### 🔹 5. Delete TODO

```
DELETE /todos/{id}
```
![delete](https://github.com/Manasvijain06/Assignments/blob/dd7dab1b861d6f34d410b32e788e47955a5a5662/manasvijain_java_training/session4/SpringAdvance/Screenshot/delete.png)

---



## 🔄 Status Transition Rules

✔ Allowed:

* PENDING → COMPLETED
* COMPLETED → PENDING

❌ Invalid transitions will return an error

---

## ⚠️ Exception Handling

Handled using `@RestControllerAdvice`:

* Todo Not Found → 404
* Invalid Status → 400
* Validation Errors → 400
  
![not found](https://github.com/Manasvijain06/Assignments/blob/e5d909ddd23cb5a11e9e87ddae3ae814f4167ca1/manasvijain_java_training/session4/SpringAdvance/Screenshot/TodoNotFound.png)

---

## Logging (MANDATORY)

Logging implemented using SLF4J Logger:

Controller Logs
Fetching TODOs
Creating TODO
Updating TODO
Deleting TODO
Service Logs
Business logic execution
Success logs
Error logs (invalid status, not found)

📌 Example Logs:

Creating new TODO with title: Task 1
TODO created Successfully with ID: 1
Notification Sending: New TODO created: Task 1

---

## Notification Service (Dummy)

A simulated external service:

NotificationServiceClient

✔ Called when TODO is created
✔ Logs message instead of real API call

Example:

Notification Sending: New TODO created: Task 1

## Unit Testing - JUnit 5 , Mockito

---

## 🛠️ How to Run the Project

### 🔹 1. Clone Repository

```
git clone <your-repo-link>
```

---

### 🔹 2. Configure Database

Update `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 🔹 3. Run Application

Using Maven:

```
mvn spring-boot:run
```

Or run `SpringAdvanceApplication.java` from IDE

---

### 🔹 4. Test APIs

Use:

* Postman
* Thunder Client

Base URL:

```
http://localhost:8080/todos
```

---



## 🧠 Concepts Demonstrated

* IoC (Inversion of Control)
* Dependency Injection (Constructor-based)
* Component Scanning
* JPA & Hibernate
* REST API Design
* DTO Pattern
* Exception Handling

---

## 👨‍💻 Author

**Manasvi Jain**

---

## 📌 Conclusion

This project demonstrates a clean and scalable backend architecture using Spring Boot, following best practices like DTO usage, validation, exception handling, and proper layering.

---

⭐ *Thank you for reviewing this project!*
