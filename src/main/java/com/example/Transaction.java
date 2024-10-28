package com.example;

import java.time.LocalDate;
public class Transaction {
    private LocalDate date;
    private int amount;
    private String category;
    private String type;

    public Transaction(LocalDate date, int amount, String category, String type) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.type = type;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public int getAmount() { return amount; }
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
