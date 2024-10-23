# **Expense Tracker Application** (Ongoing)

## **Overview**
The **Expense Tracker** is a personal finance management tool that allows users to track their income and expenses, categorize transactions, and generate monthly financial reports. This console-based application is written in Java and supports data persistence using CSV files.

## **Features** 
- Add and categorize income and expense transactions.
- View a summary of transactions by category.
- Generate monthly reports showing total income and expenses.
- Save and load transaction data using CSV files for persistence.

## **How to Run**
1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/ExpenseTracker.git
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

## **How to Use**
1. **Add Transactions**: Input your income or expenses with details such as date, amount, and category.
2. **View Transactions**: See a list of all transactions or filter by category.
3. **Generate Reports**: View a monthly report that summarizes your income and expenses.
4. **Save Data**: Your transactions are saved automatically to a CSV file, allowing you to reload your data the next time you run the application.

## **Future Features **
- Database integration using JDBC.
- Graphical User Interface (GUI) using JavaFX.
- Support for multiple users.
