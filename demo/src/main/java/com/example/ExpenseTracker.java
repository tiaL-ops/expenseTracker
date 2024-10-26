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

    /**
     * Returns a list of transactions for the specified month and year.
     * @param month The month to filter by (1 for January, 12 for December).
     * @param year The year to filter by.
     * @return A list of transactions within the specified month and year.
     */
    public List<Transaction> getTransactionsByMonth(int month, int year){
        List<Transaction> monthlyTransactions = new ArrayList<>();
        for (Transaction t : transactions){
            if(t.getDate().getMonthValue() == month && t.getDate().getYear() == year){
                monthlyTransactions.add(t);
            }
        }
        return monthlyTransactions;
    }


    /**
     * Generates a monthly report for the specified month and year.
     * @param month The month to generate the report for (1-12).
     * @param year The year to generate the report for.
     * @return A formatted string containing the report with totals and transaction details.
     */
    public String generateMonthlyReport(int month, int year){
        List<Transaction> monthlyTransactions=getTransactionsByMonth(month, year);
        int totalIncome = 0;
        int totalExpenses=0;

        StringBuilder report = new StringBuilder("Monthly Report for " + month + "/" + year + ":\n");
        report.append("---------------------------------------------------\n");

        for (Transaction t : monthlyTransactions){
            report.append(t).append("\n");
            if(t.getType().equalsIgnoreCase("Income")){
                totalIncome+=t.getAmount();
            }
            if(t.getType().equalsIgnoreCase("Expenses")){
                totalExpenses+=t.getAmount();
            }
           
        }

        int netBalance = totalIncome - totalExpenses;
        report.append("---------------------------------------------------\n");
        report.append("Total Income: ").append(totalIncome).append("\n");
        report.append("Total Expenses: ").append(totalExpenses).append("\n");
        report.append("Net Balance: ").append(netBalance).append("\n");

        return report.toString();

    }

 
}
    