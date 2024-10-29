package com.example;
import org.json.simple.JSONObject;

import java.time.LocalDate;
public class Transaction {
    private LocalDate date;
    private double amount;
    private String category;
    private String type;

    public Transaction(LocalDate date, double amount, String category, String type) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.type = type;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getType() { return type; }

    @Override
    public String toString() {
        return "Date: " + date + " Amount: " + amount + " Category: " + category + " Type: " + type;
    }

    /**
     * Converts the Transaction object into a JSONObject.
     * @return A JSONObject representing this transaction.
     */
    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();
        obj.put("date", this.date);
        obj.put("amount", this.amount);
        obj.put("category", this.category);
        obj.put("type", this.type);
        return obj;
    }
}
