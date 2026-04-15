# Java – Spring And Rest Assignment


## 📌 Project Overview

This project is a **Spring Boot-based application** developed as part of the Java training assignment. It demonstrates core backend development concepts such as layered architecture, REST APIs, dependency injection, and centralized exception handling.

The application includes:

* User Management System
* User Submission API
* User Search with Filters
* Delete User with Confirmation
* Exception Handling System

---

## Project Structure

```
com.Nucleusteq.SpringAndRestAssignment
│
├── controller
│   └── UserController.java
│
├── service
│   └── UserService.java
│
├── repository
│   └── UserRepository.java
│
├── model
│   └── User.java
│
├── exception
│   ├── UserNotFoundException.java
│   ├── InvalidUserException.java
│   ├── UserAlreadyExistsException.java
│   └── GlobalExceptionHandler.java
│
└── SpringAndRestAssignmentApplication.java
```

---

## Features Implemented

### 1️⃣ User Management System

#### APIs:

* `GET /users/search` → Search users using filters
* `POST /users/submit` → Submit (create) a new user
* `DELETE /users/{id}` → Delete user with confirmation

---

## 🔍 API DETAILS

---

### 🔎 1. Search Users

**GET** `/users/search`

#### Query Params (optional):

* `name`
* `age`
* `role`

#### Example:

```
http://localhost:8080/users/search?name=Manasvi
```

#### Response:

```json
[
  {
    "id": 1,
    "name": "Manasvi",
    "age": 21,
    "role": "Admin",
    "email": "manasvi@gmail.com"
  }
]
```

---

### 📨 2. Submit User

**POST** `/users/submit`

#### Request Body:

```json
{
  "name": "Rahul",
  "email": "rahul123@gmail.com",
  "age": 25
}
```

#### Success Response:

```
201 CREATED
User submitted successfully
```

#### Error Responses:

```
400 BAD REQUEST → Invalid input
409 CONFLICT → User already exists
```

---

### ❌ 3. Delete User

**DELETE** `/users/{id}?confirm=true`

#### Example:

```
http://localhost:8080/users/1?confirm=true
```

#### Responses:

* ✅ Success → `User deleted successfully`
* ❌ Not Found → `404 NOT FOUND`
* ❌ No Confirmation → `400 BAD REQUEST`

---

## 🧠 Concepts Used

### ✔ Spring Core Concepts

* **IoC (Inversion of Control)** → Spring manages object creation
* **Dependency Injection (Constructor-based)** → Loose coupling between layers
* **Component Scanning** → Automatic detection of annotated classes

---

### ✔ Annotations Used

* `@RestController`
* `@Service`
* `@Repository`
* `@RestControllerAdvice`
* `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@DeleteMapping`

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

* No external database is used
* Data resets on application restart

---

## ⚠️ Exception Handling

Centralized exception handling is implemented using `@RestControllerAdvice`.

### 🔹 Custom Exceptions

* **UserNotFoundException**
  → When user is not found

* **InvalidUserException**
  → When input data is invalid

* **UserAlreadyExistsException**
  → When duplicate email is used

* **Generic Exception**
  → Handles unexpected errors

---

## 🧪 Testing the APIs

### 🔹 Search API

```
GET http://localhost:8080/users/search
```

### 🔹 Submit API

```
POST http://localhost:8080/users/submit
```

### 🔹 Delete API

```
DELETE http://localhost:8080/users/{id}?confirm=true
```

---

## 📸 Screenshots

> Add screenshots of API responses using Thunder Client or Postman

### 🔹 Search API Output

![Search API](https://github.com/Manasvijain06/Assignments/blob/70ad37b9c8a70db1148016c440361072b4bd3993/manasvijain_java_training/session3/SpringAndRestAssignment/screenshots/search.png)

Search By Name:
![SearchByName](https://github.com/Manasvijain06/Assignments/blob/70ad37b9c8a70db1148016c440361072b4bd3993/manasvijain_java_training/session3/SpringAndRestAssignment/screenshots/searchByName.png)

Search By Age:
![SearchBAge](https://github.com/Manasvijain06/Assignments/blob/70ad37b9c8a70db1148016c440361072b4bd3993/manasvijain_java_training/session3/SpringAndRestAssignment/screenshots/SearchByAge.png)

Search By Role:
![Searchbyrole](https://github.com/Manasvijain06/Assignments/blob/70ad37b9c8a70db1148016c440361072b4bd3993/manasvijain_java_training/session3/SpringAndRestAssignment/screenshots/searchByRole.png)

Search By Combination:
![search](https://github.com/Manasvijain06/Assignments/blob/70ad37b9c8a70db1148016c440361072b4bd3993/manasvijain_java_training/session3/SpringAndRestAssignment/screenshots/searchByCombination.png)

---

### 🔹 Submit API Output

![Submit API](https://github.com/Manasvijain06/Assignments/blob/70ad37b9c8a70db1148016c440361072b4bd3993/manasvijain_java_training/session3/SpringAndRestAssignment/screenshots/submit.png)

![submit](https://github.com/Manasvijain06/Assignments/blob/70ad37b9c8a70db1148016c440361072b4bd3993/manasvijain_java_training/session3/SpringAndRestAssignment/screenshots/submitStatus400.png)

---

### 🔹 Delete API Output

![Delete API](https://github.com/Manasvijain06/Assignments/blob/70ad37b9c8a70db1148016c440361072b4bd3993/manasvijain_java_training/session3/SpringAndRestAssignment/screenshots/delete.png)

---

## ⚙️ Technologies Used

* Java 17
* Spring Boot
* Maven
* REST APIs
* VS Code / IntelliJ
* Thunder Client / Postman

---

## 🎯 Conclusion

This project demonstrates core **Spring Boot fundamentals** including:

* REST API development
* Layered architecture
* Exception handling
* Input validation

It is designed to be simple, clean, and aligned with best practices for backend development.

---

## 👨‍💻 Author

**Manasvi Jain**

---
