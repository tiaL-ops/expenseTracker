package com.example;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ExpenseTracker {

    private List<Transaction> transactions;

    public ExpenseTracker(){
        transactions = new ArrayList<>();
    }

    public void addIncome(LocalDate date, int amount, String category){
        Transaction income = new Transaction(date, amount, category, "income");
        transactions.add(income);
    }

    public void addExpense(LocalDate date, int amount, String category){
        Transaction expense = new Transaction(date, amount, category, "expense");
        transactions.add(expense);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Filters transactions by category and returns the list of matching transactions.
     * @param category The category to filter by.
     * @return A list of transactions that match the specified category.
     */
    public List<Transaction> filterTransactionsByCategory(String category) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getCategory().equals(category)) {
                filteredTransactions.add(t);
            }
        }
        return filteredTransactions;
    }

    /**
     * Calculates the total amount for transactions in the specified category.
     * @param category The category to sum up.
     * @return The total amount for the specified category.
     */
    public int showTotalByCategory(String category) {
        int total = 0;
        for (Transaction t : transactions) {
            if (t.getCategory().equals(category)) {
                total += t.getAmount();
            }
        }
        return total;
    }

    /**
     * Returns a string representation of all transactions for display.
     * @return A string containing all transactions.
     */
    public String displayAllTransactions() {
        StringBuilder allTransactions = new StringBuilder();
        for (Transaction t : transactions) {
            allTransactions.append(t).append("\n");
        }
        return allTransactions.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker tracker = new ExpenseTracker();
        LocalDate date = null;
        int amount = 0;

        while (date == null) {
            try {
                System.out.println("Please enter date (YYYY-MM-DD):");
                date = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date format. Please write the date in the format (YYYY-MM-DD).");
            }
        }
        while (true) {
            try {
                System.out.println("Please enter the amount:");
                amount = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid amount. Please enter a valid integer.");
            }
        }

        System.out.println("Please enter the category:");
        String category = scanner.nextLine();

        System.out.println("Please enter the type: income or expense");
        String type = scanner.nextLine();
        if (type.equalsIgnoreCase("income")) {
            tracker.addIncome(date, amount, category);
        } else if (type.equalsIgnoreCase("expense")) {
            tracker.addExpense(date, amount, category);
        } else {
            System.out.println("Invalid transaction type. Please enter either 'income' or 'expense'.");
        }

        System.out.println("Your transaction is saved!");

        // Example usage of the refactored methods
        System.out.println("All transactions:");
        System.out.println(tracker.displayAllTransactions());

        System.out.println("Filtered transactions for category " + category + ":");
        System.out.println(tracker.filterTransactionsByCategory(category));

        System.out.println("Total for category " + category + ": " + tracker.showTotalByCategory(category));

        scanner.close();
    }
}
