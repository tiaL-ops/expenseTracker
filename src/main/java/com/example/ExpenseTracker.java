package com.example;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ExpenseTracker {

    private List<Transaction> transactions;
    private Map<String, JSONObject> userMap = new HashMap<>();

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
    // Initialize the map with data from JSON
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
            if(t.getType().equalsIgnoreCase("Expense")){
                totalExpenses+=t.getAmount();
            }
           
        }
        System.out.println("randomtestcheck");

        int netBalance = totalIncome - totalExpenses;
        report.append("---------------------------------------------------\n");
        report.append("Total Income: ").append(totalIncome).append("\n");
        report.append("Total Expenses: ").append(totalExpenses).append("\n");
        report.append("Net Balance: ").append(netBalance).append("\n");

        return report.toString();

    }

    // Initialize the map with data from JSON
  

        /**
     * Save the user's transaction to a JSON file.
     * If the user already exists, the transaction will be added to their list.
     * If the user does not exist, a new entry for the user will be created.
     *
     * @param user_id The user ID to associate with the transaction.
     * @param transaction The transaction to be saved (e.g., date, amount, category, type).
     */
    public void saveTransaction(String user_id, Transaction transaction) {
        // Get or create user data in userMap
        JSONObject userObj = userMap.getOrDefault(user_id, new JSONObject());
        if (!userMap.containsKey(user_id)) {
            userObj.put("user_id", user_id);
            userObj.put("transactions", new JSONArray());
            userMap.put(user_id, userObj); 
        }


        JSONArray transactions = (JSONArray) userObj.get("transactions");
        transactions.add(transaction.toJsonObject());

        
        JSONArray usersArray = new JSONArray();
        usersArray.addAll(userMap.values());

        try (FileWriter file = new FileWriter("transactions.json")) {
            file.write(usersArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load and return a list of transactions for a given user.
     * If the user does not exist, an empty list will be returned.
     *
     * @param user_id The user ID whose transactions are being requested.
     * @return A list of Transaction objects for the specified user.
     */
    public List<Transaction> loadTransactions(String user_id) {
        List<Transaction> transactionsList = new ArrayList<>();

        // Get user data directly from the map
        JSONObject userObj = userMap.get(user_id);
        if (userObj != null) {
            JSONArray userTransactions = (JSONArray) userObj.get("transactions");
            for (Object transactionObj : userTransactions) {
                JSONObject transactionJson = (JSONObject) transactionObj;
                LocalDate date = (LocalDate) transactionJson.get("date");
                double amount = (double) transactionJson.get("amount");
                String category = (String) transactionJson.get("category");
                String type = (String) transactionJson.get("type");

                Transaction transaction = new Transaction(date, amount, category, type);
                transactionsList.add(transaction);
            }
        }
        return transactionsList;
    }

    public Map<String, JSONObject> getUserMap(){
        return userMap;
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
    