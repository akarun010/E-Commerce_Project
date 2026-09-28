# 🛒 E-Commerce Backend API

A backend REST API for an E-Commerce application built using **Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL**.

The project provides APIs for managing users, categories, products, shopping carts, cart items, and orders, with stock management and transactional order processing.

---

## 🚀 Features

* User management
* Category management 
* Product management
* Shopping cart management 
* Add/update/remove cart items
* Order creation and management
* Automatic order total calculation
* Product stock validation
* Automatic stock reduction after order placement
* Cart cleanup after successful checkout
* Order item price snapshot
* Database relationships using JPA/Hibernate
* Transactional order processing
* RESTful API architecture
* MySQL database integration
* Maven project management

### 🔐 Planned Security Features

* Spring Security
* BCrypt password encryption
* JWT authentication
* Role-based authorization
* Admin/customer access control

---

## 🛠️ Technologies Used

| Technology      | Purpose               |
| --------------- | --------------------- |
| Java            | Backend programming   |
| Spring Boot     | Application framework |
| Spring MVC      | REST API development  |
| Spring Data JPA | Database access       |
| Hibernate       | ORM                   |
| MySQL           | Database              |
| Maven           | Dependency management |
| Lombok          | Boilerplate reduction |
| Postman         | API testing           |
| Git & GitHub    | Version control       |

---

## 🏗️ Project Architecture

```text
Client / Postman
       ↓
   Controller
       ↓
     Service
       ↓
   Repository / DAO
       ↓
     JPA / Hibernate
       ↓
      MySQL
```

### Project Structure

```text
src
└── main
    └── java
        └── com.arun.E_Commerce.Project
            ├── Controller
            │   ├── UserController
            │   ├── CategoryController
            │   ├── ProductController
            │   ├── CartController
            │   └── OrderController
            │
            ├── Service
            │   ├── UserService
            │   ├── CategoryService
            │   ├── ProductService
            │   ├── CartService
            │   └── OrderService
            │
            ├── DAO
            │   ├── UserDAO
            │   ├── CategoryDAO
            │   ├── ProductDAO
            │   ├── CartDAO
            │   ├── CartItemDAO
            │   ├── OrderDAO
            │   └── OrderItemDAO
            │
            └── Model
                ├── User
                ├── Category
                ├── Product
                ├── Cart
                ├── CartItem
                ├── Order
                └── OrderItem
```

---

# 🗄️ Database Design

The application uses **MySQL**.

### Main Tables

```text
users
categories
products
cart
cart_item
orders
order_items
```

### Relationships

```text
User
 ├── 1 : 1 ── Cart
 └── 1 : N ── Order

Category
 └── 1 : N ── Product

Cart
 └── 1 : N ── CartItem

Product
 ├── 1 : N ── CartItem
 └── 1 : N ── OrderItem

Order
 └── 1 : N ── OrderItem
```

---

# 👤 User Module

Provides basic user CRUD operations.

### Endpoints

```http
POST   /users
GET    /users/{userId}
GET    /users
PUT    /users
DELETE /users/{userId}
```

User information includes:

* Name
* Email
* Password
* Phone
* Address
* Role

> Password encryption and authentication will be handled through Spring Security and BCrypt.

---

# 🏷️ Category Module

Categories are used to organize products.

Example:

```text
Electronics
Clothing
Books
```

Categories have a one-to-many relationship with products.

---

# 📦 Product Module

Products contain:

* Name
* Description
* Price
* Quantity
* Category
* Created date

The product quantity represents the available stock.

Example:

```text
Product: Mechanical Keyboard
Price: ₹1500
Stock: 10
```

---

# 🛒 Cart Module

Each user has one shopping cart.

A cart can contain multiple cart items.

### Cart APIs

```http
GET    /cart/{cartId}

POST   /cart/{cartId}/product/{productId}/quantity/{quantity}

PUT    /cart/{cartItemId}/quantity/{quantity}

DELETE /cart/{cartItemId}
```

### Cart Example

```text
Cart
 ├── Keyboard × 2
 ├── Mouse × 1
 └── Headset × 1
```

The application prevents users from adding more products than the available stock.

---

# 📋 Order Module

The Order module handles the checkout process.

### APIs

```http
POST /orders/{cartId}

GET  /orders/{orderId}

GET  /orders
```

## Order Creation Flow

```text
Cart
  ↓
Get Cart Items
  ↓
Check Stock
  ↓
Calculate Total
  ↓
Create Order
  ↓
Create Order Items
  ↓
Reduce Product Stock
  ↓
Remove Purchased Cart Items
```

The entire process is handled inside a transaction using:

```java
@Transactional
```

This helps keep the order creation process consistent if an operation fails.

---

# 💰 Order Calculation

The order total is calculated using `BigDecimal`.

For example:

```text
Keyboard
Price = ₹1500
Quantity = 2

1500 × 2 = ₹3000
```

The final order amount is stored in the `orders` table.

---

# 📸 Price Snapshot

`OrderItem` stores the product price at the time of purchase.

Example:

```text
Product price today = ₹1500

OrderItem
price = ₹1500
quantity = 2
```

If the product price later changes to ₹1800, the old order still retains the original purchase price of ₹1500.

---

# 📦 Stock Management

Before placing an order, the application checks whether sufficient stock exists.

```text
Available stock = 10
Requested quantity = 3

10 >= 3
      ↓
Order allowed
```

After the order:

```text
10 - 3 = 7
```

The product stock is updated automatically.

---

# 🧪 API Testing

The APIs can be tested using **Postman**.

Recommended testing flow:

```text
1. Create User
       ↓
2. Create Category
       ↓
3. Create Product
       ↓
4. Create Cart
       ↓
5. Add Product to Cart
       ↓
6. View Cart
       ↓
7. Create Order
       ↓
8. Check Order
       ↓
9. Verify Stock
       ↓
10. Verify Cart Items
```

---

# 🔐 Security Roadmap

The project is designed to support authentication and authorization.

Planned security implementation:

```text
Spring Security
      ↓
BCrypt Password Hashing
      ↓
JWT Authentication
      ↓
JWT Filter
      ↓
Role-Based Authorization
```

Roles:

```text
USER
ADMIN
```

Example future access control:

```text
USER
 ├── Manage own cart
 ├── Create orders
 └── View own orders

ADMIN
 ├── Manage products
 ├── Manage categories
 ├── View orders
 └── Update order status
```

---

# ⚠️ Validation & Exception Handling

The project will use validation and centralized exception handling for cases such as:

* Invalid user data
* Invalid product data
* Product not found
* Cart not found
* Order not found
* Insufficient stock
* Invalid quantities
* Invalid IDs

A global exception handler will provide consistent API error responses.

---

# 📝 Logging

Application logging will be used to track important backend operations and errors.

Logging will help with:

* Request processing
* Business operations
* Database-related errors
* Exceptions
* Debugging

---

# 🧪 Unit Testing

The project is designed to include:

* JUnit
* Mockito
* Service-layer unit testing
* Repository interaction testing
* Exception scenario testing

Important test cases include:

```text
Create User
Create Product
Add Product to Cart
Insufficient Stock
Create Order
Stock Reduction
Empty Cart
Invalid Order ID
```

---

# ⚙️ Setup & Installation

## 1. Clone the Repository

```bash
git clone <your-github-repository-url>
```

## 2. Open the Project

Open the project in:

```text
IntelliJ IDEA
Eclipse
VS Code
```

## 3. Configure MySQL

Create a database:

```sql
CREATE DATABASE ecommerce;
```

Update your Spring Boot database configuration with your MySQL credentials.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## 4. Build the Project

```bash
mvn clean install
```

## 5. Run the Application

Run the Spring Boot application.

Default URL:

```text
http://localhost:8080
```

---

# 🔗 API Overview

| Module | Method | Endpoint                                                 |
| ------ | ------ | -------------------------------------------------------- |
| User   | POST   | `/users`                                                 |
| User   | GET    | `/users/{userId}`                                        |
| User   | GET    | `/users`                                                 |
| User   | PUT    | `/users`                                                 |
| User   | DELETE | `/users/{userId}`                                        |
| Cart   | GET    | `/cart/{cartId}`                                         |
| Cart   | POST   | `/cart/{cartId}/product/{productId}/quantity/{quantity}` |
| Cart   | PUT    | `/cart/{cartItemId}/quantity/{quantity}`                 |
| Cart   | DELETE | `/cart/{cartItemId}`                                     |
| Order  | POST   | `/orders/{cartId}`                                       |
| Order  | GET    | `/orders/{orderId}`                                      |
| Order  | GET    | `/orders`                                                |

---

# 🎯 Project Goals

This project demonstrates practical experience with:

* Java backend development
* Spring Boot
* REST API development
* Layered architecture
* Spring Data JPA
* Hibernate
* MySQL
* Entity relationships
* Transaction management
* E-Commerce business logic
* Stock management
* API testing
* Git & GitHub

---

# 🚧 Future Improvements

* [ ] Spring Security
* [ ] BCrypt password encryption
* [ ] JWT authentication
* [ ] Role-based authorization
* [ ] DTOs
* [ ] Global exception handling
* [ ] Bean Validation
* [ ] Logging
* [ ] JUnit & Mockito tests
* [ ] Pagination and sorting
* [ ] Product search/filtering
* [ ] Admin dashboard APIs
* [ ] Order status management
* [ ] API documentation with Swagger/OpenAPI
* [ ] Docker
* [ ] Deployment
* [ ] CI/CD

---

# 👨‍💻 Author

**Arun Dev**

Diploma Computer Science Engineering Student
Java Full Stack Developer

### Profiles

* GitHub: https://github.com/akarun010
* LinkedIn: https://www.linkedin.com/in/akarunkumar010

---

## ⭐ Project Status

**Backend Development:** 🚧 In Progress

The core E-Commerce backend modules are implemented, with security, validation, testing, documentation, Docker, and deployment planned as subsequent stages.
