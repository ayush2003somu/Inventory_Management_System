📦 Inventory Management System (Java + JDBC)

A console-based Inventory Management System built using Core Java and JDBC, designed to manage products efficiently with features like adding, updating, deleting, searching, issuing stock, and restocking.
The project follows object-oriented principles and uses MySQL Database for persistent data storage.

🚀 Features

Add new products with validation

Update existing product details

Delete products from inventory

View complete inventory in tabular format

Search products by Product ID

Issue stock with quantity validation

Restock products

Low-stock warning indicator

Custom exception handling

Persistent storage using MySQL (JDBC)

🛠️ Technologies Used

Java (Core Java)

JDBC (MySQL Connector/J)

MySQL Database

Collections Framework

Object-Oriented Programming (OOP)

Exception Handling

Prepared Statements

Scanner (User Input)

🗄️ Database Integration (JDBC)

This project uses JDBC (Java Database Connectivity) to interact with a MySQL database.

🔹 Why JDBC?

To store inventory data permanently

To ensure data consistency and reliability

To handle large-scale data efficiently

To follow industry-standard database practices

🔹 JDBC Concepts Used

DriverManager and Connection

PreparedStatement

ResultSet

Parameterized SQL queries (SQL Injection safe)

CRUD operations (Create, Read, Update, Delete)

📁 Database Structure
📌 Table: inventory
Column Name	Data Type
id	VARCHAR (Primary Key)
name	VARCHAR
price	DOUBLE
quantity	INT
🧩 Core Classes Explanation
🔸 Product Class

Represents an inventory item (Entity / Model)

Attributes: id, name, price, quantity

Contains validation logic

Overrides toString() for formatted console output

🔸 InventoryManagementSystem

Main controller class

Handles user interaction and business logic

Communicates with database using JDBC

Uses PreparedStatement for database operations

🔸 InventoryException

Custom exception for inventory-related errors

Improves error clarity and maintainability


🖥️ Sample Output
------------------------------------------------------------
| ID       | NAME                 | PRICE      | QTY      |
------------------------------------------------------------

| P101     | Keyboard             | 999.50     | 3        | ⚠ Low Stock

| P102     | Mouse                | 499.00     | 10       |
------------------------------------------------------------
Total Inventory Value: 6494.00


📌 Key Concepts Demonstrated

JDBC & Database Connectivity

Prepared Statements

Custom Exceptions

Input Validation

Encapsulation & Abstraction

Clean Console UI

Separation of Concerns (Model vs Logic)

🎯 Future Enhancements

DAO Pattern implementation

Connection Pooling

GUI using JavaFX or Swing

User authentication & roles

Export inventory to CSV / Excel

REST API using Spring Boot

👨‍💻 Author

Ayush Srivastava
B.Tech (3rd Year) | Java, JDBC & DSA Enthusiast

⭐ If you like this project

Don’t forget to ⭐ star the repository!
