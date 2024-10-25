import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.Transient;
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

      
        assertEquals(1, tracker.getTransactions().size());


        Transaction transaction = tracker.getTransactions().get(0);
        assertEquals("income", transaction.getType());
        assertEquals(1000, transaction.getAmount());
        assertEquals("Salary", transaction.getCategory());
        assertEquals(LocalDate.of(2023, 10, 5), transaction.getDate());
    }

    @Test
    public void testAddExpense() {
       
        tracker.addExpense(LocalDate.of(2023, 10, 6), 200, "Groceries");

      
        assertEquals(1, tracker.getTransactions().size());

        
        Transaction transaction = tracker.getTransactions().get(0);
        assertEquals("expense", transaction.getType());
        assertEquals(200, transaction.getAmount());
        assertEquals("Groceries", transaction.getCategory());
        assertEquals(LocalDate.of(2023, 10, 6), transaction.getDate());
    }

    @Test
    public void testShowTotalByCategory() {
        
        tracker.addExpense(LocalDate.of(2023, 10, 5), 50, "Groceries");
        tracker.addExpense(LocalDate.of(2023, 10, 6), 20, "Groceries");
        tracker.addExpense(LocalDate.of(2023, 10, 7), 30, "Groceries");

        
        tracker.addExpense(LocalDate.of(2023, 10, 7), 100, "Rent");

        
        int expectedTotal = 50 + 20 + 30;
        
        tracker.showTotalByCategory("Groceries");
        
    }

}
