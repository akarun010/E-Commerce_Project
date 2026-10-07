# 🛒 E-Commerce Backend API

A backend-only **E-Commerce REST API** built with **Java and Spring Boot** as a hands-on full-stack development learning project.

The project focuses on building a real-world backend using **REST APIs, Spring Data JPA, Hibernate, MySQL, Spring Security, JWT authentication, role-based authorization, validation, exception handling, logging, and unit testing with JUnit and Mockito**.

---

## 🚀 Features

### 👤 User Management
- User registration
- User authentication
- BCrypt password encryption
- Get user details
- Update user details
- Delete users
- Role-based access control
- User ownership authorization

### 📂 Category Management
- Create category
- Get category by ID
- Get all categories
- Update category
- Delete category
- Admin-only modification operations

### 📦 Product Management
- Create product
- Get product by ID
- Get all products
- Update product
- Delete product
- Product quantity/stock management
- Category-based product relationship

### 🛒 Cart Management
- Create cart for users
- Add products to cart
- Update cart item quantity
- Remove products from cart
- View cart
- Stock validation
- Prevent unauthorized cart access
- Admin access where applicable

### 🧾 Order Management
- Create orders from cart
- Generate order items
- Calculate order amount
- Store purchase-time product price
- Reduce product stock after order creation
- Clear ordered cart items
- View individual orders
- Admin access to all orders
- User ownership validation

### 🔐 Security
- Spring Security
- JWT authentication
- BCrypt password hashing
- Stateless authentication
- Role-based authorization
- `USER` and `ADMIN` roles
- Resource ownership authorization

### 🧪 Testing
Unit testing using:

- JUnit 5
- Mockito
- Arrange → Act → Assert pattern
- Mockito `@Mock`
- Mockito `@InjectMocks`
- `when()`
- `verify()`
- `Optional.of()`
- `Optional.empty()`
- Authentication mocking with `SecurityContextHolder`

Service-layer tests have been implemented for:

- ProductService ✅
- CategoryService ✅
- UserService ✅
- OrderService ✅

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

### Build Tool
- Maven

### API Testing
- Postman

### Development Tools
- Eclipse / IDE
- Git
- GitHub

### Logging
- SLF4J
- Logback
- `@Slf4j`

---

## 🏗️ Project Architecture

The project follows a layered backend architecture:

```text
Controller
    ↓
Service
    ↓
DAO / Repository
    ↓
JPA / Hibernate
    ↓
MySQL
```

### Main Layers

**Controller Layer**
- Handles HTTP requests
- Maps REST endpoints
- Receives request data
- Returns HTTP responses

**Service Layer**
- Contains business logic
- Performs validation
- Handles authorization/ownership logic
- Communicates with repositories

**DAO / Repository Layer**
- Handles database operations
- Uses Spring Data JPA

**Model / Entity Layer**
- Represents database tables
- Defines entity relationships

**Security Layer**
- JWT authentication
- User authentication
- Role-based authorization
- Security filters

---

## 🗃️ Database Entities

The project contains the following main entities:

```text
User
Cart
Category
Product
CartItem
Order
OrderItem
```

### Entity Relationships

```text
User
 │
 ├── 1 : 1 ── Cart
 │              │
 │              └── 1 : Many ── CartItem
 │                                │
 │                                └── Many : 1 ── Product
 │
 └── 1 : Many ── Order
                    │
                    └── 1 : Many ── OrderItem
                                      │
                                      └── Many : 1 ── Product

Category
   │
   └── 1 : Many ── Product
```

---

## 🔑 Authentication Flow

The application uses JWT-based stateless authentication.

```text
User Login
    ↓
Credentials Verified
    ↓
JWT Generated
    ↓
Client Sends JWT
    ↓
JWT Filter
    ↓
Token Validation
    ↓
SecurityContextHolder
    ↓
Authenticated Request
```

Protected APIs require a valid JWT token.

Example:

```text
Authorization: Bearer <JWT_TOKEN>
```

---

## 👥 Roles

The application supports two main roles:

### USER

Users can:

- Access their own account
- Manage their own cart
- Create orders for their cart
- Access their own resources

### ADMIN

Admins can:

- Manage products
- Manage categories
- Access users
- Access orders
- Perform administrative operations

Authorization is handled through both **Spring Security configuration** and service-level ownership checks.

---

## 📡 API Modules

### User APIs

```text
POST   /users
GET    /users/{id}
GET    /users
PUT    /users/{id}
DELETE /users/{id}
```

### Category APIs

```text
POST   /categories
GET    /categories/{id}
GET    /categories
PUT    /categories/{id}
DELETE /categories/{id}
```

### Product APIs

```text
POST   /products
GET    /products/{id}
GET    /products
PUT    /products/{id}
DELETE /products/{id}
```

### Cart APIs

```text
GET    /carts/{id}
POST   /carts/{id}/items
PUT    /carts/{id}/items/{itemId}
DELETE /carts/{id}/items/{itemId}
```

### Order APIs

```text
POST   /orders/{cartId}
GET    /orders/{orderId}
GET    /orders
```

> Endpoint paths may vary slightly depending on the final controller mappings.

---

## 🧮 Order Creation Flow

When a user creates an order:

```text
Cart
 ↓
Get Cart Items
 ↓
Check Product Stock
 ↓
Calculate Total Amount
 ↓
Create Order
 ↓
Create OrderItems
 ↓
Reduce Product Stock
 ↓
Delete CartItems
 ↓
Order Created
```

The `OrderItem` stores the product price at the time of purchase.

This prevents the order's historical price from changing when the product's current price changes later.

---

## 🔒 Validation & Exception Handling

The project includes:

- Request validation
- Stock validation
- Authentication checks
- Authorization checks
- Ownership checks
- Resource-not-found handling
- Unauthorized-access handling
- Global exception handling

---

## 📝 Logging

Logging is implemented using:

```text
SLF4J
Logback
@Slf4j
```

Different log levels are used depending on the situation:

```text
INFO   → Successful operations
WARN   → Invalid/unauthorized situations
DEBUG  → Debugging information
ERROR  → Application errors
```

Example operations that are logged:

- User authentication
- Product operations
- Cart operations
- Order creation
- Unauthorized access
- Missing resources
- Insufficient stock

---

## 🧪 Unit Testing

The project uses **JUnit 5 and Mockito** for service-layer unit testing.

Testing follows:

```text
Arrange
   ↓
Act
   ↓
Assert
   ↓
Verify
```

Example testing concepts used:

```java
@Mock
@InjectMocks
when()
verify()
assertEquals()
assertNull()
Optional.of()
Optional.empty()
```

Authentication-dependent services are tested by mocking:

```text
Authentication
       ↓
SecurityContextHolder
       ↓
UserDAO
       ↓
Authenticated User
```

### Current Testing Progress

```text
ProductServiceTest   ✅
CategoryServiceTest  ✅
UserServiceTest      ✅
OrderServiceTest     ✅
CartServiceTest      ⏳
```

---

## 🧰 Running the Project

### 1. Clone the repository

```bash
git clone <your-repository-url>
```

### 2. Open the project

Open the project using your preferred Java IDE.

### 3. Configure MySQL

Create a MySQL database.

Example:

```sql
CREATE DATABASE ecommerce;
```

### 4. Configure application properties

Set your database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

Configure the remaining Spring Boot properties according to your local environment.

### 5. Build the project

```bash
mvn clean install
```

### 6. Run the application

```bash
mvn spring-boot:run
```

Or run the main Spring Boot application class directly from your IDE.

---

## 🧪 Testing APIs with Postman

The APIs can be tested using Postman.

Typical testing flow:

```text
1. Register User
       ↓
2. Login
       ↓
3. Receive JWT
       ↓
4. Add JWT to Authorization Header
       ↓
5. Create/View Products
       ↓
6. Create/View Cart
       ↓
7. Add Products to Cart
       ↓
8. Create Order
       ↓
9. Verify Stock
       ↓
10. Verify Order
```

---

## 📁 Project Structure

```text
src
 └── main
      └── java
           └── com.arun.E_Commerce.Project
                │
                ├── Controller
                │
                ├── Service
                │
                ├── DAO
                │
                ├── Model
                │
                ├── Security
                │
                ├── Exception
                │
                └── ...
```

Tests:

```text
src
 └── test
      └── java
           └── com.arun.E_Commerce.Project
                │
                └── Service
                     ├── ProductServiceTest
                     ├── CategoryServiceTest
                     ├── UserServiceTest
                     ├── OrderServiceTest
                     └── CartServiceTest
```

---

## 🎯 Learning Objectives

This project was created to gain practical experience with:

- Building REST APIs with Spring Boot
- Designing relational databases
- JPA entity relationships
- Hibernate ORM
- Spring Data JPA
- Layered architecture
- Authentication and authorization
- JWT security
- BCrypt password hashing
- Role-based access control
- Ownership-based authorization
- Input validation
- Exception handling
- Logging
- JUnit
- Mockito
- Maven
- Postman API testing
- Git and GitHub

---

## 📌 Project Status

### Completed

- [x] Database design
- [x] Entity creation
- [x] JPA relationships
- [x] DAO/Repository layer
- [x] User management
- [x] Category management
- [x] Product management
- [x] Cart logic
- [x] Order logic
- [x] REST controllers
- [x] JWT authentication
- [x] BCrypt password hashing
- [x] Role-based authorization
- [x] Ownership authorization
- [x] Validation
- [x] Exception handling
- [x] Logging
- [x] Postman API testing
- [x] ProductService unit tests
- [x] CategoryService unit tests
- [x] UserService unit tests
- [x] OrderService unit tests

### In Progress

- [ ] CartService unit tests
- [ ] Final project cleanup
- [ ] Final API verification

---

## 📚 What I Learned

Through this project, I practiced how different Spring Boot concepts work together in a complete backend application:

```text
Java
 ↓
Spring Boot
 ↓
REST API
 ↓
JPA / Hibernate
 ↓
MySQL
 ↓
Spring Security
 ↓
JWT
 ↓
Validation & Exceptions
 ↓
Logging
 ↓
JUnit & Mockito
```

The main goal of the project was not just to create CRUD APIs, but to understand how authentication, authorization, database relationships, business logic, testing, and REST APIs work together in a backend application.

---

## 👨‍💻 Author

**Arun Dev**

Diploma Computer Science Engineering Student  
Java Full Stack Developer — Learning & Building

### Core Skills

```text
Java
Spring Boot
Spring Data JPA
Hibernate
MySQL
REST APIs
Spring Security
JWT
React
JavaScript
HTML
CSS
Tailwind CSS
JUnit
Mockito
Maven
Git
GitHub
```

---

## ⭐ Project Purpose

This project is a **learning-focused backend project** created to strengthen practical Java Full Stack development skills and prepare for larger full-stack applications and future projects.