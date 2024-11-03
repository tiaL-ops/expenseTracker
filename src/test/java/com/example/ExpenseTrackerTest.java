package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExpenseTrackerTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private ExpenseTracker expenseTracker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testAddIncome() {
        LocalDate date = LocalDate.of(2023, 10, 5);
        expenseTracker.addIncome("user1", date, 1000, "Salary");

        // Verify that the transaction is saved with the correct parameters
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testAddExpense() {
        LocalDate date = LocalDate.of(2023, 10, 6);
        expenseTracker.addExpense("user1", date, 200, "Groceries");

        // Verify that the transaction is saved with the correct parameters
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testGetTransactions() {
        // Mock the repository to return some example transactions
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(
                new Transaction(LocalDate.of(2023, 10, 5), 1000, "Salary", "income", "user1"),
                new Transaction(LocalDate.of(2023, 10, 6), 200, "Groceries", "expense", "user1")
        ));

        List<Transaction> transactions = expenseTracker.getTransactions();

        // Assert that the returned transactions match the mock data
        assertEquals(2, transactions.size(), "Transaction count should match the mock data.");
        assertEquals("Salary", transactions.get(0).getCategory(), "First transaction category should be 'Salary'.");
        assertEquals("Groceries", transactions.get(1).getCategory(), "Second transaction category should be 'Groceries'.");
    }

    @Test
    public void testFilterTransactionsByCategory() {
        // Mock the repository to return transactions in various categories
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(
                new Transaction(LocalDate.of(2023, 10, 5), 50, "Groceries", "expense", "user1"),
                new Transaction(LocalDate.of(2023, 10, 6), 20, "Groceries", "expense", "user1"),
                new Transaction(LocalDate.of(2023, 10, 7), 30, "Utilities", "expense", "user1")
        ));

        List<Transaction> groceries = expenseTracker.filterTransactionsByCategory("Groceries");

        // Assert that only "Groceries" transactions are returned
        assertEquals(2, groceries.size(), "Filtered transactions should only include 'Groceries' category.");
        for (Transaction t : groceries) {
            assertEquals("Groceries", t.getCategory(), "Each transaction should be in the 'Groceries' category.");
        }
    }

    @Test
    public void testShowTotalByCategory() {
        // Mock the repository to return transactions in different categories
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(
                new Transaction(LocalDate.of(2023, 10, 5), 50, "Groceries", "expense", "user1"),
                new Transaction(LocalDate.of(2023, 10, 6), 20, "Groceries", "expense", "user1"),
                new Transaction(LocalDate.of(2023, 10, 7), 30, "Groceries", "expense", "user1"),
                new Transaction(LocalDate.of(2023, 10, 7), 100, "Rent", "expense", "user1")
        ));

        double totalGroceries = expenseTracker.showTotalByCategory("Groceries");
        assertEquals(100, totalGroceries, "Total for 'Groceries' should be the sum of all grocery expenses.");

        double totalRent = expenseTracker.showTotalByCategory("Rent");
        assertEquals(100, totalRent, "Total for 'Rent' should be 100.");
    }

    @Test
    public void testGenerateMonthlyReport() {
        // Mock transactions for a specific month
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(
                new Transaction(LocalDate.of(2023, 10, 1), 1500, "Salary", "income", "user1"),
                new Transaction(LocalDate.of(2023, 10, 2), 300, "Rent", "expense", "user1"),
                new Transaction(LocalDate.of(2023, 10, 3), 100, "Groceries", "expense", "user1"),
                new Transaction(LocalDate.of(2023, 10, 15), 150, "Utilities", "expense", "user1")
        ));

        String report = expenseTracker.generateMonthlyReport(10, 2023);

        // Check if report contains expected values
        assertTrue(report.contains("Total Income: 1500.0"), "Report should contain total income of 1500.");
        assertTrue(report.contains("Total Expenses: 550.0"), "Report should contain total expenses of 550.");
        assertTrue(report.contains("Net Balance: 950.0"), "Report should contain net balance of 950.");
    }
}
