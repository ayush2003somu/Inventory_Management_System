# 📦 Inventory Management System (Java + JDBC)

A console-based **Inventory Management System** built using **Core Java** and **JDBC**, designed to manage products efficiently with features like adding, updating, deleting, searching, issuing stock, and restocking. 

The project follows object-oriented principles and uses **MySQL Database** for persistent data storage.

## 🚀 Features
* **Add new products** with real-time validation.
* **Update existing product details** seamlessly.
* **Delete products** from the inventory database.
* **View complete inventory** in a clean, tabular format.
* **Search products** instantly by Product ID.
* **Issue stock** with quantity validation (prevents over-issuing).
* **Restock products** to maintain inventory levels.
* **Low-stock warning indicator** (visual alert for items below a threshold).
* **Custom exception handling** for robust error management.
* **Persistent storage** using MySQL via JDBC.

## 🛠️ Technologies Used
* **Java:** Core Java (JDK 8+)
* **JDBC:** MySQL Connector/J
* **Database:** MySQL
* **Collections Framework:** For temporary data handling
* **OOPs:** Encapsulation, Abstraction, and Polymorphism
* **Security:** Prepared Statements to prevent SQL Injection
* **I/O:** Scanner class for console-based user interaction

## 🗄️ Database Integration (JDBC)
This project uses JDBC (Java Database Connectivity) to interact with a MySQL database to ensure data consistency and reliability.

### 🔹 JDBC Concepts Used
* **DriverManager & Connection:** Establishing the link between Java and MySQL.
* **PreparedStatement:** For executing parameterized SQL queries safely.
* **ResultSet:** To iterate through and display database records.
* **CRUD Operations:** Implementation of Create, Read, Update, and Delete.

### 📌 Database Schema: `inventory`
| Column Name | Data Type | Constraints |
| :--- | :--- | :--- |
| `id` | VARCHAR(50) | Primary Key |
| `name` | VARCHAR(100) | Not Null |
| `price` | DOUBLE | Not Null |
| `quantity` | INT | Not Null |

## 🧩 Core Classes Explanation
* **`Product` Class:** The Entity model representing an item (Attributes: `id`, `name`, `price`, `quantity`).
* **`InventoryManagementSystem`:** The main controller handling business logic and user input.
* **`DBHandler` (or Logic Layer):** Manages all SQL operations and JDBC connections.
* **`InventoryException`:** A custom exception class to handle inventory-specific errors like "Insufficient Stock."

## 🖥️ Sample Output
```text
------------------------------------------------------------
| ID       | NAME                 | PRICE      | QTY      |
------------------------------------------------------------
| P101     | Keyboard             | 999.50     | 3        | ⚠ Low Stock
| P102     | Mouse                | 499.00     | 10       |
------------------------------------------------------------
Total Inventory Value: 6494.00
```

🎯 Future Enhancements
DAO Pattern: Implementation of Data Access Object pattern for better abstraction.

Connection Pooling: Using HikariCP for improved database performance.

GUI: Building a front-end using JavaFX or Swing.

Auth: User authentication and role-based access (Admin/Staff).

Export: Exporting inventory reports to CSV or Excel formats.


Author: Ayush Srivastava

B.Tech (3rd Year) | Java, JDBC & DSA Enthusiast

