# 📦 Inventory Management System (Java)

A **console-based Inventory Management System** built using **Core Java**, designed to manage products efficiently with features like adding, updating, deleting, searching, issuing stock, and restocking.  
The project follows **object-oriented principles** and uses **File Management** to persist inventory data.

---

## 🚀 Features

- Add new products with validation
- Update existing product details
- Delete products from inventory
- View complete inventory in tabular format
- Search products by ID or name
- Issue stock with quantity validation
- Restock products
- Low-stock warning indicator
- Custom exception handling
- Persistent storage using **File Management**

---

## 🛠️ Technologies Used

- **Java (Core Java)**
- **Collections Framework (HashMap)**
- **Object-Oriented Programming (OOP)**
- **Exception Handling**
- **File Management (File I/O)**
- **Scanner (User Input)**

---

## 📁 File Management (Persistence)

This project uses **File Handling** to store inventory data permanently.

### 🔹 Why File Management?
- To **save inventory data** even after the program stops
- To **load products automatically** when the program restarts
- Acts as a lightweight alternative to a database

### 🔹 Concepts Used
- File reading and writing
- Serialization / text-based storage (depending on implementation)
- Data persistence across executions

---


---

## 🧩 Core Classes Explanation

### 🔸 Product Class
- Represents an inventory item
- Attributes: `id`, `name`, `price`, `quantity`
- Contains validation logic
- Overrides `toString()` for clean output

### 🔸 InventoryManagementSystem
- Main controller class
- Handles user interaction
- Manages inventory using `HashMap`
- Implements all inventory operations

### 🔸 InventoryException
- Custom exception for inventory-related errors
- Improves readability and error handling

---

## 🖥️ Sample Output

| ID | NAME | PRICE | QTY |
| P101 | Keyboard | 999.50 | 3 | ⚠ LOW STOCK
| P102 | Mouse | 499.00 | 10 |

Total Inventory Value: 6494.00


---

## 📌 Key Concepts Demonstrated

- Method Overriding (`toString()`)
- Custom Exceptions
- Input Validation
- Encapsulation
- Collections
- File Handling
- Clean Console UI

---

## 🎯 Future Enhancements

- Database integration (MySQL)
- GUI using JavaFX or Swing
- User authentication
- Export inventory to CSV
- Role-based access

---

## 👨‍💻 Author

**Ayush Srivastava**  
B.Tech (3rd Year) | Java & DSA Enthusiast  

---

## ⭐ If you like this project

Don’t forget to ⭐ star the repository!


