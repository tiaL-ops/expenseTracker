package com.example;

import java.time.LocalDate;

public class ExpenseTrackerApp {

    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        
        // Add some example transactions
        tracker.addIncome(LocalDate.of(2023, 10, 1), 1500, "Salary");
        tracker.addExpense(LocalDate.of(2023, 10, 2), 300, "Rent");
        tracker.addExpense(LocalDate.of(2023, 10, 3), 100, "Groceries");
        tracker.addExpense(LocalDate.of(2023, 10, 15), 150, "Utilities");

        // Generate and print the monthly report for October 2023
        System.out.println(tracker.generateMonthlyReport(10, 2023));
    }
}
