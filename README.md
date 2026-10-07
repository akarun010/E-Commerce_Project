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

The project contains **7 main entities**:

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

### Important Design Decisions

- One user has one cart.
- A cart can contain multiple cart items.
- A product can belong to multiple cart items.
- A user can have multiple orders.
- An order contains multiple order items.
- `OrderItem` stores the product price at the time of purchase.
- Product stock is reduced after successful order creation.

---

# 👤 User Management

The application supports user registration and user account management.

### Features

- User registration
- BCrypt password encryption
- Get user details
- Update user
- Delete user
- Authentication
- Role-based authorization
- Ownership validation

Users can access their own resources, while administrators have broader access depending on the operation.

---

# 📂 Category Management

The category module provides CRUD operations for product categories.

### Features

- Create category
- Get category by ID
- Get all categories
- Update category
- Delete category

Administrative operations are protected using authorization.

---

# 📦 Product Management

The product module manages products available in the store.

### Features

- Create product
- Get product by ID
- Get all products
- Update product
- Delete product
- Product quantity management
- Product-category relationship

Product stock is also used during cart and order operations.

---

# 🛒 Cart Management

The cart system allows authenticated users to manage products before placing an order.

### Features

- View cart
- Add product to cart
- Update product quantity
- Remove cart item
- Stock validation
- Existing cart-item quantity handling
- User ownership validation

### Cart Flow

```text
User
 ↓
Cart
 ↓
Add Product
 ↓
Check Stock
 ↓
CartItem Created / Updated
 ↓
Update Quantity
 ↓
Create Order
```

---

# 🧾 Order Management

Orders are created from the user's cart.

### Order Creation Flow

```text
Cart
 ↓
Get Cart Items
 ↓
Check Product Stock
 ↓
Calculate Total
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

### Order Features

- Create order from cart
- Calculate total amount
- Create order items
- Store purchase-time price
- Reduce product stock
- Clear cart items after successful order
- View individual orders
- Admin access to orders
- Ownership authorization

---

# 🔐 Security

The application uses **Spring Security and JWT** for authentication and authorization.

### Authentication Flow

```text
User Login
    ↓
Credentials Validation
    ↓
JWT Token Generated
    ↓
Client Stores Token
    ↓
Request With JWT
    ↓
JWT Filter
    ↓
Token Validation
    ↓
SecurityContextHolder
    ↓
Authenticated Request
```

JWT is sent using:

```text
Authorization: Bearer <JWT_TOKEN>
```

### Security Features

- JWT authentication
- Stateless authentication
- BCrypt password hashing
- Role-based authorization
- User ownership authorization
- Protected endpoints
- SecurityContextHolder authentication

---

# 👥 Roles

The project uses two primary roles.

## USER

A normal user can:

- Access their own account
- Manage their own cart
- Add products to their cart
- Update cart quantities
- Remove cart items
- Create orders for their cart
- Access resources they own

## ADMIN

An administrator can perform administrative operations such as:

- Product management
- Category management
- User management
- Order management
- Accessing resources where admin authorization is permitted

---

# 🧪 Unit Testing


The application uses **JUnit 5 and Mockito** for service-layer unit testing.

Testing follows the:

```text
Arrange
   ↓
Act
   ↓
Assert
   ↓
Verify
```

### Mockito Concepts Practiced

```java
@Mock
@InjectMocks

when()
verify()
```

Also practiced:

```java
Optional.of()
Optional.empty()
assertEquals()
assertNull()
```

Authentication-dependent services were tested by mocking:

```text
Authentication
      ↓
SecurityContextHolder
      ↓
UserDAO
      ↓
Authenticated User
```

---

## ✅ Testing Status

All major service classes have been tested successfully.

| Service | Status |
|---|---|
| ProductService | ✅ Completed |
| CategoryService | ✅ Completed |
| UserService | ✅ Completed |
| CartService | ✅ Completed |
| OrderService | ✅ Completed |

### Tested Functionality

- Successful operations
- Resource-not-found scenarios
- Authentication-dependent operations
- Database interaction verification
- Repository method calls
- Cart operations
- Order creation
- Stock-related order processing

---

# 📝 Logging

Logging is implemented using **SLF4J + Logback**.

Example logging levels:

```text
INFO
WARN
DEBUG
ERROR
```

Logging is used for important application events such as:

- Successful operations
- Authentication-related events
- Order creation
- Unauthorized access
- Missing resources
- Insufficient stock
- Cart operations

---

# ⚠️ Exception Handling

The application handles common backend errors including:

- Resource not found
- Unauthorized access
- Invalid operations
- Insufficient stock
- Validation errors

A global exception-handling approach is used to provide appropriate HTTP responses.

---

# ✔️ Validation

Validation is applied to incoming request data to prevent invalid information from entering the application.

Examples include:

- Required fields
- Valid input values
- Product quantity validation
- Cart quantity validation
- Stock availability checks

---

# 📡 REST API Modules

The project contains REST endpoints for:

```text
Users
Categories
Products
Carts
Orders
Authentication
```

Typical operations include:

```text
POST    → Create
GET     → Read
PUT     → Update
DELETE  → Delete
```

The exact endpoint mappings are defined in the project's controller classes.

---

# 📮 API Testing With Postman

The application was tested using **Postman**.

Typical workflow:

```text
1. Register User
       ↓
2. Login
       ↓
3. Receive JWT
       ↓
4. Add JWT to Authorization Header
       ↓
5. Access Protected APIs
       ↓
6. Create/View Products
       ↓
7. Add Product to Cart
       ↓
8. Update Cart
       ↓
9. Create Order
       ↓
10. Verify Stock & Order
```

---

# 🏗️ Project Structure

```text
E-Commerce-Project/
│
├── src/
│   │
│   ├── main/
│   │   ├── java/
│   │   │   └── com/arun/E_Commerce/Project/
│   │   │       │
│   │   │       ├── Controller/
│   │   │       ├── Service/
│   │   │       ├── DAO/
│   │   │       ├── Model/
│   │   │       ├── Security/
│   │   │       └── Exception/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/arun/E_Commerce/Project/
│               └── Service/
│                   ├── ProductServiceTest
│                   ├── CategoryServiceTest
│                   ├── UserServiceTest
│                   ├── CartServiceTest
│                   └── OrderServiceTest
│
├── images/
│   ├── architecture.png
│   ├── database-erd.png
│   ├── jwt-flow.png
│   ├── order-flow.png
│   └── testing.png
│
├── pom.xml
└── README.md
```

---

# ⚙️ How to Run

## 1. Clone the Repository

```bash
git clone <your-repository-url>
```

## 2. Open the Project

Open the project in Eclipse, IntelliJ IDEA, or another Java IDE.

## 3. Create MySQL Database

Example:

```sql
CREATE DATABASE ecommerce;
```

## 4. Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

Use your own local database credentials.

## 5. Build the Project

```bash
mvn clean install
```

## 6. Run the Application

```bash
mvn spring-boot:run
```

Or run the Spring Boot main application class from your IDE.

---

# 📚 What I Learned

This project helped me understand how different Java backend technologies work together:

```text
Java
 ↓
Spring Boot
 ↓
REST APIs
 ↓
Spring Data JPA
 ↓
Hibernate
 ↓
MySQL
 ↓
Spring Security
 ↓
JWT
 ↓
Validation
 ↓
Exception Handling
 ↓
Logging
 ↓
JUnit + Mockito
```

More importantly, I learned how to connect these concepts into a complete backend application instead of studying them individually.

---

# 🎯 Key Skills Demonstrated

```text
Java
Spring Boot
REST API Development
Spring Data JPA
Hibernate
MySQL
JPA Relationships
Spring Security
JWT Authentication
BCrypt
Role-Based Authorization
Ownership Authorization
Validation
Exception Handling
SLF4J / Logback
JUnit 5
Mockito
Maven
Postman
Git
GitHub
```

---

# 📊 Project Completion

```text
Database Design          ✅
Entity Relationships    ✅
Repository Layer        ✅
Service Layer           ✅
REST Controllers        ✅
User Management         ✅
Category Management     ✅
Product Management      ✅
Cart Management         ✅
Order Management        ✅
JWT Security            ✅
Authorization           ✅
Validation              ✅
Exception Handling      ✅
Logging                 ✅
Postman Testing         ✅
JUnit Testing           ✅
Mockito Testing         ✅
```

## 🏁 Project Status: COMPLETED ✅

This project is considered **complete for its learning objectives**.

No additional production-level features were added unnecessarily; the focus was on strengthening core Java Full Stack backend skills.

---

# 👨‍💻 Author

## Arun Dev

**Diploma Computer Science Engineering Student**

Focused on **Java Full Stack Development**

### Current Learning Stack

```text
Java
Spring Boot
Spring Security
JPA / Hibernate
MySQL
REST APIs
JUnit
Mockito
React
JavaScript
HTML
CSS
Tailwind CSS
Git
GitHub
Maven
```

---

# ⭐ Final Note

This E-Commerce Backend project was built as a practical learning project to strengthen my understanding of **Java Full Stack backend development**.

The project gave me hands-on experience with **REST API development, database relationships, authentication, authorization, business logic, testing, logging, and API testing**.

🚀 **Built. Tested. Completed.**
