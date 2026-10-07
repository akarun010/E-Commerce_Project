# 🛒 E-Commerce Backend REST API

A backend-only **E-Commerce REST API** built using **Java, Spring Boot, Spring Data JPA, Hibernate, MySQL, Spring Security, JWT, JUnit, and Mockito**.

This project was built as a hands-on learning project to understand how a real-world Java backend is designed, secured, tested, and connected to a relational database.

---

## 📌 Project Overview

This application provides backend functionality for an online store, including **user management, product and category management, shopping cart operations, order processing, authentication, authorization, validation, logging, and unit testing**.

The project follows a layered architecture and uses Spring Data JPA/Hibernate for database interaction.

### 🎯 Main Goals

- Build REST APIs using Spring Boot
- Work with JPA and Hibernate
- Design relational entity relationships
- Implement JWT authentication
- Implement role-based authorization
- Implement ownership-based authorization
- Handle cart and order business logic
- Validate product stock
- Implement exception handling and logging
- Practice unit testing with JUnit and Mockito
- Test APIs using Postman

---

## 🖼️ Project Architecture

![Project Architecture](images/architecture.png)

The application follows a layered architecture:

```text
Client / Postman
       ↓
Controller Layer
       ↓
Service Layer
       ↓
DAO / Repository Layer
       ↓
JPA / Hibernate
       ↓
MySQL Database
```

Security, validation, logging, and testing work across the application.

---

## 🛠️ Tech Stack

### Backend

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- BCrypt

### Database

- MySQL

### Testing

- JUnit 5
- Mockito
- Spring Boot Starter Test

### Tools

- Maven
- Postman
- Git
- GitHub
- Eclipse / IDE

### Logging

- SLF4J
- Logback
- Lombok `@Slf4j`

---

# 🗄️ Database Design

![Database ER Diagram](images/database-erd.png)

The project contains **7 main entities**:

```text
User
Cart
Category
