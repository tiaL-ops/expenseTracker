package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize a mock or test User object
        testUser = new User();
        testUser.setUsername("user1");
        testUser.setPassword("password");
        testUser.setEmail("user1@example.com");
    }

    @Test
    public void testAddIncome() {
        LocalDate date = LocalDate.of(2023, 10, 5);
        expenseTracker.addIncome(date, 1000, "Salary", testUser);

        // Capture the Transaction object saved to the repository
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(1)).save(transactionCaptor.capture());
        
        Transaction savedTransaction = transactionCaptor.getValue();
        
        // Assert that the Transaction has correct properties
        assertEquals("income", savedTransaction.getType(), "Transaction type should be 'income'.");
        assertEquals(1000, savedTransaction.getAmount(), "Transaction amount should be 1000.");
        assertEquals("Salary", savedTransaction.getCategory(), "Transaction category should be 'Salary'.");
        assertEquals(testUser, savedTransaction.getUserId(), "Transaction user should match the test user.");
    }

    @Test
    public void testAddExpense() {
        LocalDate date = LocalDate.of(2023, 10, 6);
        expenseTracker.addExpense(date, 200, "Groceries", testUser);

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(1)).save(transactionCaptor.capture());

        Transaction savedTransaction = transactionCaptor.getValue();
        
        // Assert that the Transaction has correct properties
        assertEquals("expense", savedTransaction.getType(), "Transaction type should be 'expense'.");
        assertEquals(200, savedTransaction.getAmount(), "Transaction amount should be 200.");
        assertEquals("Groceries", savedTransaction.getCategory(), "Transaction category should be 'Groceries'.");
        assertEquals(testUser, savedTransaction.getUserId(), "Transaction user should match the test user.");
    }

    @Test
    public void testGetTransactions() {
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(
                new Transaction(LocalDate.of(2023, 10, 5), 1000, "Salary", "income", testUser),
                new Transaction(LocalDate.of(2023, 10, 6), 200, "Groceries", "expense", testUser)
        ));

        List<Transaction> transactions = expenseTracker.getTransactions();

        assertEquals(2, transactions.size(), "Transaction count should match the mock data.");
        assertEquals("Salary", transactions.get(0).getCategory(), "First transaction category should be 'Salary'.");
        assertEquals("Groceries", transactions.get(1).getCategory(), "Second transaction category should be 'Groceries'.");
        assertEquals(testUser, transactions.get(0).getUserId(), "First transaction user should match test user.");
        assertEquals(testUser, transactions.get(1).getUserId(), "Second transaction user should match test user.");
    }

    @Test
    public void testFilterTransactionsByCategory() {
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(
                new Transaction(LocalDate.of(2023, 10, 5), 50, "Groceries", "expense", testUser),
                new Transaction(LocalDate.of(2023, 10, 6), 20, "Groceries", "expense", testUser),
                new Transaction(LocalDate.of(2023, 10, 7), 30, "Utilities", "expense", testUser)
        ));

        List<Transaction> groceries = expenseTracker.filterTransactionsByCategory("Groceries");

        assertEquals(2, groceries.size(), "Filtered transactions should only include 'Groceries' category.");
        for (Transaction t : groceries) {
            assertEquals("Groceries", t.getCategory(), "Each transaction should be in the 'Groceries' category.");
            assertEquals(testUser, t.getUserId(), "Each transaction user should match the test user.");
        }
    }

    @Test
    public void testShowTotalByCategory() {
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(
                new Transaction(LocalDate.of(2023, 10, 5), 50, "Groceries", "expense", testUser),
                new Transaction(LocalDate.of(2023, 10, 6), 20, "Groceries", "expense", testUser),
                new Transaction(LocalDate.of(2023, 10, 7), 30, "Groceries", "expense", testUser),
                new Transaction(LocalDate.of(2023, 10, 7), 100, "Rent", "expense", testUser)
        ));

        double totalGroceries = expenseTracker.showTotalByCategory("Groceries");
        assertEquals(100, totalGroceries, "Total for 'Groceries' should be the sum of all grocery expenses.");

        double totalRent = expenseTracker.showTotalByCategory("Rent");
        assertEquals(100, totalRent, "Total for 'Rent' should be 100.");
    }

    @Test
    public void testGenerateMonthlyReport() {
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(
                new Transaction(LocalDate.of(2023, 10, 1), 1500, "Salary", "income", testUser),
                new Transaction(LocalDate.of(2023, 10, 2), 300, "Rent", "expense", testUser),
                new Transaction(LocalDate.of(2023, 10, 3), 100, "Groceries", "expense", testUser),
                new Transaction(LocalDate.of(2023, 10, 15), 150, "Utilities", "expense", testUser)
        ));

        String report = expenseTracker.generateMonthlyReport(10, 2023);

        assertTrue(report.contains("Total Income: 1500.0"), "Report should contain total income of 1500.");
        assertTrue(report.contains("Total Expenses: 550.0"), "Report should contain total expenses of 550.");
        assertTrue(report.contains("Net Balance: 950.0"), "Report should contain net balance of 950.");
    }
}
