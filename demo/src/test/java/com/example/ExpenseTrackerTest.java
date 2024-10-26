package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class ExpenseTrackerTest {

    private ExpenseTracker tracker;

    @BeforeEach
    public void setUp() {
        tracker = new ExpenseTracker();
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
}
