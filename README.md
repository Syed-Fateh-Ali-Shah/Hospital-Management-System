# Hospital-Management-System
DSA project using JAVA

Description
A Java Swing-based application for managing hospital records, including patients, doctors, and medical supplies. The system features an admin login, tabbed interface for different entities, and CRUD (Create, Read, Update, Delete) operations with a user-friendly GUI.

Features
Admin Login: Secure login with username/password validation.
Tabbed Interface: Separate tabs for Patients, Doctors, and Medical Supplies.
CRUD Operations:
Add new records.
Delete records by name.
Read and display all records in a table.
Update records by selecting from the table and editing fields.
Data Persistence: Uses linked lists for in-memory storage (can be extended for database integration).
User-Friendly GUI: Custom fonts, colors, and layouts for better usability.
Requirements
Java Development Kit (JDK) 8 or higher.
An IDE like Eclipse, IntelliJ IDEA, or NetBeans for compilation and running.
No external libraries required (uses standard Java Swing and AWT).
Installation
Clone or download the project files.
Ensure all Java files are in the same directory or package.
Compile the Java files using the command line or your IDE:

Copy code
javac *.java
Run the application:

Copy code
java HospitalGUI
Usage
Login: Enter "admin" for both username and password to access the system.
Navigate Tabs: Switch between "Patients", "Doctors", and "Medical Supplies".
Operations:
Add: Fill in the fields and click the "Add" button.
Delete: Enter the name and click the "Delete" button.
Read: Click the "Read" button to view all records in a table dialog.
Update: Click "Read" to open the table, select a row, edit the populated fields, and click "Update".
Messages: Success or error messages will appear for each operation.
Exit: Close the window to exit the application.
File Structure
HospitalGUI.java: Main GUI class with login, tabs, and panels.
Patient.java: Patient class with name, age, and disease.
Doctor.java: Doctor class with name, specialization, CNIC, date, and time.
MedicalSupply.java: MedicalSupply class with name, quantity, and supplier.
HospitalInventory.java: Generic inventory class for managing lists of entities.
Node.java: Node class for linked list implementation.
Contributing
Feel free to fork the repository and submit pull requests for improvements. Ensure code follows Java best practices and includes comments.

License
This project is open-source and available under the MIT License. Feel free to use and modify as needed.

For any issues or questions, please refer to the code comments or contact the developer.
