# **Expense Tracker Application** (Ongoing)

## **Overview**
The **Expense Tracker** is a personal finance management tool that allows users to track income and expenses, categorize transactions, and generate monthly financial reports. Initially developed as a console-based Java application, it now integrates a frontend to enhance user experience.

## **Features**
- Add and categorize income and expense transactions.
- View a summary of transactions by category.
- Generate monthly reports showing total income and expenses.
- Data persistence using CSV files (currently in progress to migrate to SQL database).

## **Current Progress (Updated after each milestone)**

### **Basic Functionality (Day 10 - Completed)**
- Core expense tracking functions completed: add transactions, filter by category, view reports.
- CSV-based data persistence implemented.
- Backend API and database setup underway to support enhanced functionality in the application.

### **Next Phase: User Interface and Frontend Development**
- Transitioning to a web application format, using React for the frontend.
- Integrating with the backend to support real-time data updates, interactive forms, and a responsive user experience.

## **How to Run the Console Application (for testing basic functionalities)**
1. **Clone the repository**:
   ```bash
   git clone https://github.com/tiaL-Ops/ExpenseTracker.git
   cd ExpenseTracker
   ```

2. **Compile the Java application**:
   ```bash
   javac -d bin src/com/expensetracker/*.java
   ```

3. **Run the application**:
   ```bash
   java -cp bin com.expensetracker.ExpenseTracker
   ```

## **How to Use (Console Application)**
1. **Add Transactions**: Input your income or expenses with details such as date, amount, and category.
2. **View Transactions**: See a list of all transactions or filter by category.
3. **Generate Reports**: View a monthly report that summarizes your income and expenses.
4. **Save Data**: Your transactions are saved automatically to a CSV file, allowing you to reload your data the next time you run the application.

## **Future Features**
- Database integration using SQL for robust data storage.
- Web-based frontend using React, with a seamless API connection to the backend.
- Support for multiple users and authentication features.

