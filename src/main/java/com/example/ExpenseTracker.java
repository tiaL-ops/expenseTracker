package com.example;

import java.util.*;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ExpenseTracker {

    private List<Transaction> transactions;
    private Map<String, JSONObject> userMap;
    private String filePath;

    public ExpenseTracker() {
        this.transactions = new ArrayList<>();
        this.userMap = new HashMap<>();
        this.filePath = "transactions.json";
    }

    public ExpenseTracker(String jsonFilePath) {
        this.transactions = new ArrayList<>();
        this.userMap = new HashMap<>();
        this.filePath = jsonFilePath;
        loadFromJson(jsonFilePath);
    }

    private void loadFromJson(String filePath) {
        JSONArray usersArray = parseJsonFile(filePath);
        if (usersArray != null) {
            for (Object obj : usersArray) {
                JSONObject userObj = (JSONObject) obj;
                String userId = (String) userObj.get("user_id");
                userMap.put(userId, userObj);
                JSONArray userTransactions = (JSONArray) userObj.get("transactions");
                for (Object txnObj : userTransactions) {
                    JSONObject txnJson = (JSONObject) txnObj;
                    LocalDate date = LocalDate.parse((String) txnJson.get("date"));
                    double amount = ((Number) txnJson.get("amount")).doubleValue();
                    String category = (String) txnJson.get("category");
                    String type = (String) txnJson.get("type");

                    Transaction transaction = new Transaction(date, amount, category, type, userId);
                    transactions.add(transaction);
                }
            }
        }
    }

    public void addIncome(String userId, LocalDate date, double amount, String category) {
        Transaction income = new Transaction(date, amount, category, "income", userId);
        transactions.add(income);
        saveTransaction(userId, income);
    }

    public void addExpense(String userId, LocalDate date, double amount, String category) {
        Transaction expense = new Transaction(date, amount, category, "expense", userId);
        transactions.add(expense);
        saveTransaction(userId, expense);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void loadAllUsers() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("transactions.json")) {
            JSONArray usersArray = (JSONArray) parser.parse(reader);
            for (Object obj : usersArray) {
                JSONObject userObj = (JSONObject) obj;
                String userId = (String) userObj.get("user_id");
                userMap.put(userId, userObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> filterTransactionsByCategory(String category) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getCategory().equalsIgnoreCase(category)) {
                filteredTransactions.add(t);
            }
        }
        return filteredTransactions;
    }

    public double showTotalByCategory(String category) {
        double total = 0;
        for (Transaction t : transactions) {
            if (t.getCategory().equalsIgnoreCase(category)) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public String displayAllTransactions() {
        StringBuilder allTransactions = new StringBuilder();
        for (Transaction t : transactions) {
            allTransactions.append(t).append("\n");
        }
        return allTransactions.toString();
    }

    public List<Transaction> getTransactionsByMonth(int month, int year) {
        List<Transaction> monthlyTransactions = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getDate().getMonthValue() == month && t.getDate().getYear() == year) {
                monthlyTransactions.add(t);
            }
        }
        return monthlyTransactions;
    }

    public String generateMonthlyReport(int month, int year) {
        List<Transaction> monthlyTransactions = getTransactionsByMonth(month, year);
        double totalIncome = 0;
        double totalExpenses = 0;
        StringBuilder report = new StringBuilder("Monthly Report for " + month + "/" + year + ":\n");
        report.append("---------------------------------------------------\n");

        for (Transaction t : monthlyTransactions) {
            report.append(t).append("\n");
            if (t.getType().equalsIgnoreCase("income")) {
                totalIncome += t.getAmount();
            } else if (t.getType().equalsIgnoreCase("expense")) {
                totalExpenses += t.getAmount();
            }
        }

        double netBalance = totalIncome - totalExpenses;
        report.append("---------------------------------------------------\n");
        report.append("Total Income: ").append(totalIncome).append("\n");
        report.append("Total Expenses: ").append(totalExpenses).append("\n");
        report.append("Net Balance: ").append(netBalance).append("\n");

        return report.toString();
    }

    public void saveTransaction(String userId, Transaction transaction) {
        JSONObject userObj = userMap.getOrDefault(userId, new JSONObject());
        if (!userMap.containsKey(userId)) {
            userObj.put("user_id", userId);
            userObj.put("transactions", new JSONArray());
            userMap.put(userId, userObj);
        }
        
        JSONArray userTransactions = (JSONArray) userObj.get("transactions");
        if (transaction != null && transaction.toJsonObject() != null) {
            userTransactions.add(transaction.toJsonObject());
        }
        userMap.put(userId, userObj);

        JSONArray usersArray = new JSONArray();
        usersArray.addAll(userMap.values());

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(usersArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> loadTransactions(String user_id) {
        List<Transaction> transactionsList = new ArrayList<>();
        JSONObject userObj = userMap.get(user_id);
        if (userObj != null) {
            JSONArray userTransactions = (JSONArray) userObj.get("transactions");
            for (Object transactionObj : userTransactions) {
                JSONObject transactionJson = (JSONObject) transactionObj;
                LocalDate date = LocalDate.parse((String) transactionJson.get("date"));
                double amount = ((Number) transactionJson.get("amount")).doubleValue();
                String category = (String) transactionJson.get("category");
                String type = (String) transactionJson.get("type");

                Transaction transaction = new Transaction(date, amount, category, type, user_id);
                transactionsList.add(transaction);
            }
        }
        return transactionsList;
    }

    public JSONArray parseJsonFile(String filePath) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            Object obj = parser.parse(reader);
            if (obj instanceof JSONArray) {
                return (JSONArray) obj;
            } else {
                throw new IllegalArgumentException("The JSON file does not contain an array at the root.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
