package com.example;
import org.json.simple.JSONObject;
import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.util.List;

public class ExpenseTrackerTest {
  
    private ExpenseTracker tracker;
    private String testFilePath = "test_transactions.json";
    private ExpenseTracker expenseTracker;

    @BeforeEach
    public void setUp() {
        tracker = new ExpenseTracker();
        expenseTracker = new ExpenseTracker();
        Path path = Paths.get(testFilePath);
        if (!Files.exists(path)) {
    
    try {
        Files.write(path, "[]".getBytes(), StandardOpenOption.CREATE);
    } catch (Exception e) {
        System.err.println("Well it's not there");
    }
    }
}

    @Test
    public void testAddIncome() {
        tracker.addIncome(LocalDate.of(2023, 10, 5), 1000, "Salary");

        // Verify transaction count
        assertEquals(1, tracker.getTransactions().size(), "Transaction count should be 1 after adding income.");

        
        Transaction transaction = tracker.getTransactions().get(0);
        assertEquals("income", transaction.getType(), "Transaction type should be 'income'.");
        assertEquals(1000, transaction.getAmount(), "Transaction amount should be 1000.");
        assertEquals("Salary", transaction.getCategory(), "Transaction category should be 'Salary'.");
        assertEquals(LocalDate.of(2023, 10, 5), transaction.getDate(), "Transaction date should match the input date.");
    }

    @Test
    public void testAddExpense() {
        tracker.addExpense(LocalDate.of(2023, 10, 6), 200, "Groceries");


        assertEquals(1, tracker.getTransactions().size(), "Transaction count should be 1 after adding expense.");

      
        Transaction transaction = tracker.getTransactions().get(0);
        assertEquals("expense", transaction.getType(), "Transaction type should be 'expense'.");
        assertEquals(200, transaction.getAmount(), "Transaction amount should be 200.");
        assertEquals("Groceries", transaction.getCategory(), "Transaction category should be 'Groceries'.");
        assertEquals(LocalDate.of(2023, 10, 6), transaction.getDate(), "Transaction date should match the input date.");
    }

    @Test
    public void testFilterTransactionsByCategory() {
        // Add transactions in different categories
        tracker.addExpense(LocalDate.of(2023, 10, 5), 50, "Groceries");
        tracker.addExpense(LocalDate.of(2023, 10, 6), 20, "Groceries");
        tracker.addExpense(LocalDate.of(2023, 10, 7), 30, "Utilities");

        
        List<Transaction> filteredTransactions = tracker.filterTransactionsByCategory("Groceries");

    
        assertEquals(2, filteredTransactions.size(), "Filtered transactions should include only 'Groceries' category transactions.");
        for (Transaction t : filteredTransactions) {
            assertEquals("Groceries", t.getCategory(), "Each transaction should be in the 'Groceries' category.");
        }
    }

    @Test
    public void testShowTotalByCategory() {
        // Add multiple transactions
        tracker.addExpense(LocalDate.of(2023, 10, 5), 50, "Groceries");
        tracker.addExpense(LocalDate.of(2023, 10, 6), 20, "Groceries");
        tracker.addExpense(LocalDate.of(2023, 10, 7), 30, "Groceries");
        tracker.addExpense(LocalDate.of(2023, 10, 7), 100, "Rent");

       
        int totalGroceries = tracker.showTotalByCategory("Groceries");
        assertEquals(100, totalGroceries, "Total for 'Groceries' should be the sum of all grocery expenses.");

        
        int totalEntertainment = tracker.showTotalByCategory("Entertainment");
        assertEquals(0, totalEntertainment, "Total for 'Entertainment' should be 0 since no transactions exist for this category.");
    }

    @Test
    public void testDisplayAllTransactions() {
        // Add multiple transactions
        tracker.addIncome(LocalDate.of(2023, 10, 1), 1500, "Salary");
        tracker.addExpense(LocalDate.of(2023, 10, 2), 300, "Rent");
        tracker.addExpense(LocalDate.of(2023, 10, 3), 50, "Utilities");

      
        String allTransactions = tracker.displayAllTransactions();

        
        assertTrue(allTransactions.contains("Salary"), "All transactions should include 'Salary' category.");
        assertTrue(allTransactions.contains("Rent"), "All transactions should include 'Rent' category.");
        assertTrue(allTransactions.contains("Utilities"), "All transactions should include 'Utilities' category.");
    }

    @Test
    public void testGenerateMonthlyReport() {
        // Add example transactions for October 2023
        tracker.addIncome(LocalDate.of(2023, 10, 1), 1500, "Salary");
        tracker.addExpense(LocalDate.of(2023, 10, 2), 300, "Rent");
        tracker.addExpense(LocalDate.of(2023, 10, 3), 100, "Groceries");
        tracker.addExpense(LocalDate.of(2023, 10, 15), 150, "Utilities");

 
        int expectedTotalIncome = 1500;
        int expectedTotalExpenses = 300 + 100 + 150;
        int expectedNetBalance = expectedTotalIncome - expectedTotalExpenses;

    
        String report = tracker.generateMonthlyReport(10, 2023);

        assertTrue(report.contains("Total Income: " + expectedTotalIncome), "Income mismatch in report");
        assertTrue(report.contains("Total Expenses: " + expectedTotalExpenses), "Expenses mismatch in report");
        assertTrue(report.contains("Net Balance: " + expectedNetBalance), "Net Balance mismatch in report");
    }
   


    @Test
    public void testSaveTransaction_NewUser() {
        // Arrange
        String userId = "user1";
        LocalDate date = LocalDate.parse("2023-10-27");
        
        Transaction transaction = new Transaction(date, 100.00, "Groceries", "Expense");

        // Act
        expenseTracker.saveTransaction(userId, transaction);

        // Assert
        JSONArray usersArray = expenseTracker.parseJsonFile(testFilePath);
        assertEquals(1, usersArray.size(), "Only one user should exist in the file");

        JSONObject user = (JSONObject) usersArray.get(0);
        assertEquals(userId, user.get("user_id"), "User ID should match");

        JSONArray transactions = (JSONArray) user.get("transactions");
        assertEquals(1, transactions.size(), "User should have one transaction");

        JSONObject savedTransaction = (JSONObject) transactions.get(0);
        assertEquals("2023-10-27", savedTransaction.get("date"));
        assertEquals(100.00, savedTransaction.get("amount"));
        assertEquals("Groceries", savedTransaction.get("category"));
        assertEquals("Expense", savedTransaction.get("type"));
    }

    @Test
    public void testLoadTransactions_ExistingUser() {
        
        String userId = "user1";
        LocalDate date = LocalDate.parse("2023-10-27");
        Transaction transaction = new Transaction(date, 100.00, "Groceries", "Expense");
        expenseTracker.saveTransaction(userId, transaction);

    
        List<Transaction> transactions = expenseTracker.loadTransactions(userId);

        
        assertEquals(1, transactions.size(), "User should have one transaction");

        Transaction loadedTransaction = transactions.get(0);
        assertEquals("2023-10-27", loadedTransaction.getDate(), "Date should match saved transaction");
        assertEquals(100.00, loadedTransaction.getAmount(), "Amount should match saved transaction");
        assertEquals("Groceries", loadedTransaction.getCategory(), "Category should match saved transaction");
        assertEquals("Expense", loadedTransaction.getType(), "Type should match saved transaction");
    }

    @Test
    public void testLoadTransactions_NonExistentUser() {
        // Act
        List<Transaction> transactions = expenseTracker.loadTransactions("nonexistent_user");

        // Assert
        assertTrue(transactions.isEmpty(), "Non-existent user should return an empty list of transactions");
    }

    @Test
    public void testLoadAllUsers() {
       
        String userId1 = "user1";
        String userId2 = "user2";
        LocalDate date1 = LocalDate.parse("2023-10-27");
        LocalDate date = LocalDate.parse("2023-10-28");
        Transaction transaction1 = new Transaction(date1, 100.00, "Groceries", "Expense");
        Transaction transaction2 = new Transaction(date, 200.00, "Rent", "Expense");
        expenseTracker.saveTransaction(userId1, transaction1);
        expenseTracker.saveTransaction(userId2, transaction2);


        expenseTracker.loadAllUsers(); 

      
        assertEquals(2, expenseTracker.getUserMap().size(), "There should be two users loaded into the map");

        JSONObject user1 = expenseTracker.getUserMap().get("user1");
        assertNotNull(user1, "User1 should be present in the user map");
        assertEquals("user1", user1.get("user_id"), "User1 ID should match");

        JSONObject user2 = expenseTracker.getUserMap().get("user2");
        assertNotNull(user2, "User2 should be present in the user map");
        assertEquals("user2", user2.get("user_id"), "User2 ID should match");
    }

    @AfterEach
    public void tearDown() throws IOException {
    Files.deleteIfExists(Path.of(testFilePath)); 
}

}
