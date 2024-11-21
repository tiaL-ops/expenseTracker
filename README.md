# **Expense Tracker Application** (Ongoing)

## **Overview**
The **Expense Tracker** is a personal finance management tool that allows users to track income and expenses, categorize transactions, and generate monthly financial reports. Initially developed as a console-based Java application, it now integrates a frontend to enhance user experience.

## **Features**
- Add and categorize income and expense transactions.
- View a summary of transactions by category.
- Generate monthly reports showing total income and expenses.
- SQL Database , linked with user

## **Current Milestone IN progress: Login/Logout **
- SQL Database linked with User and Transaction ✅
-Spring Security
-Jwt Auth


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

