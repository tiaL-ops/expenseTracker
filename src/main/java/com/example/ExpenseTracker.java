package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseTracker {

    private final TransactionRepository transactionRepository;

    @Autowired
    public ExpenseTracker(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void addIncome(String userId, LocalDate date, double amount, String category) {
        Transaction income = new Transaction(date, amount, category, "income", userId);
        transactionRepository.save(income);
    }

    public void addExpense(String userId, LocalDate date, double amount, String category) {
        Transaction expense = new Transaction(date, amount, category, "expense", userId);
        transactionRepository.save(expense);
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> filterTransactionsByCategory(String category) {
        return transactionRepository.findAll().stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public double showTotalByCategory(String category) {
        return filterTransactionsByCategory(category).stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public List<Transaction> getTransactionsByMonth(int month, int year) {
        return transactionRepository.findAll().stream()
                .filter(t -> t.getDate().getMonthValue() == month && t.getDate().getYear() == year)
                .collect(Collectors.toList());
    }

    public String generateMonthlyReport(int month, int year) {
        List<Transaction> monthlyTransactions = getTransactionsByMonth(month, year);
        double totalIncome = monthlyTransactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("income"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpenses = monthlyTransactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("expense"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double netBalance = totalIncome - totalExpenses;
        return String.format("Monthly Report for %d/%d:\nTotal Income: %.2f\nTotal Expenses: %.2f\nNet Balance: %.2f",
                month, year, totalIncome, totalExpenses, netBalance);
    }
}
