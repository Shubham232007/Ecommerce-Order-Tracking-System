🛒 E-Commerce Order Tracking System

📌 Project Description

This project is a Java Swing + MySQL based desktop application used to manage and track e-commerce orders.

It allows users to:

- Add customers
- Add products
- Place orders
- View orders
- Update order status
- Delete orders

---

🚀 Features

- Add Customer
- Add Product
- Add Order
- View Orders (with customer & product details)
- Update Order Status
- Delete Order

---

🛠️ Technologies Used

- Java (Swing GUI)
- MySQL (Database)
- JDBC (Database Connectivity)

---

🗄️ Database Structure

Customers Table

- customer_id (Primary Key)
- customer_name
- phone
- city

Products Table

- product_id (Primary Key)
- product_name
- price

Orders Table

- order_id (Primary Key)
- customer_id (Foreign Key)
- product_id (Foreign Key)
- quantity
- order_date
- status

---

⚙️ How to Run

Step 1: Setup MySQL

1. Open MySQL Workbench
2. Run the SQL script to create database and tables

Step 2: Configure Java

- Add MySQL Connector JAR
- Update DBConnection.java with your MySQL password

Step 3: Run Project

- Run OrderTrackingSystem.java

---

📷 Output Screens

- Add Customer
- Add Product
- Order Management GUI
- Order Table View

---

👨‍💻 Team Members

- Member 1: GUI Design
- Member 2: Database Design
- Member 3: Backend (JDBC)
- Member 4: Testing & Integration

---

📚 Viva Questions

Q1: What is JDBC?
JDBC is an API used to connect Java with databases.

Q2: What is a Foreign Key?
It links one table with another table.

Q3: Why PreparedStatement?
It prevents SQL injection and improves performance.

Q4: What is Swing?
Swing is a Java library used to build GUI applications.

---

📌 Conclusion

This project demonstrates integration of Java GUI with MySQL database and performs complete CRUD operations.
