# 📘 Java Assignment – Spring Boot Project

## 📌 Project Overview

This project is a **Spring Boot-based application** developed as part of the Java training assignment. It demonstrates core backend development concepts such as layered architecture, REST APIs, dependency injection, and component-based design.

The application includes:

* User Management System
* Notification System
* Dynamic Message Formatter System

---

## 🏗️ Project Structure

```
com.nucleusteq.user_management
│
├── controller
│   ├── UserController.java
│   ├── NotificationController.java
│   └── MessageController.java
│
├── service
│   ├── UserService.java
│   ├── NotificationService.java
│   └── MessageService.java
│
├── repository
│   └── UserRepository.java
│
├── model
│   └── User.java
│
├── component
│   ├── NotificationComponent.java
│   ├── MessageFormatter.java
│   ├── ShortMessageFormatter.java
│   └── LongMessageFormatter.java
│
└── exception
    ├── UserNotFoundException.java
    ├── DuplicateUserException.java
    └── global exception handler
    
```

---

## 🚀 Features Implemented

### 1️⃣ User Management System

#### APIs:

* `GET /users` → Fetch all users
* `POST /users` → Create a new user
* `GET /users/{id}` → Fetch user by ID

---

### 2️⃣ Notification System

#### API:

* `GET /notify` → Trigger notification

#### Description:

* Uses a `NotificationComponent` to generate messages.
* Demonstrates separation of concerns and reusable logic.

---

### 3️⃣ Dynamic Message Formatter System

#### API:

* `GET /message?type=SHORT`
* `GET /message?type=LONG`

#### Description:

* Returns different messages based on request type.
* Uses multiple components:

  * `ShortMessageFormatter`
  * `LongMessageFormatter`
  
* Runtime decision handled in Service layer (no if-else in controller).

---

## 🧠 Concepts Used

### ✔ Spring Core Concepts

* **IoC (Inversion of Control)** → Spring manages object creation
* **Dependency Injection (Constructor-based)** → Loose coupling between layers
* **Component Scanning** → Automatic detection of annotated classes

### ✔ Annotations Used

* `@RestController`
* `@Service`
* `@Repository`
* `@Component`
* `@RequestMapping`, `@GetMapping`, `@PostMapping`

---

## 🧱 Layered Architecture

```
Controller → Service → Repository → Model
```

* **Controller** → Handles HTTP requests
* **Service** → Contains business logic
* **Repository** → Handles data storage
* **Model** → Represents data structure

---

## 💾 In-Memory Data Handling

* Data is stored using:

  ```java
  private List<User> users = new ArrayList<>();
  ```
* No external database is used.
* Data is temporary and resets on application restart.

---

## ⚠️ Exception Handling

This project includes centralized exception handling using:

🔹 Custom Exceptions
UserNotFoundException
→ Thrown when requested user does not exist
🔹 DuplicateUserException
→ Thrown when trying to add a user with an existing ID

---

## 🧪 Testing the APIs

### User APIs

* `http://localhost:8080/users`
* `http://localhost:8080/users/{id}`

### Notification API

* `http://localhost:8080/notify`

### Message Formatter API

* `http://localhost:8080/message?type=SHORT`
* `http://localhost:8080/message?type=LONG`

---

## ⚙️ Technologies Used

* Java 17
* Spring Boot
* Maven
* REST APIs
* VS Code

---


## 🎯 Conclusion

This project successfully demonstrates core **Spring Boot fundamentals** including REST API development, layered architecture, dependency injection, and dynamic component handling. It is designed to be simple, clean, and aligned with industry best practices.

---

## 👨‍💻 Author

**Manasvi Jain**

