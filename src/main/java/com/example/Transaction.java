package com.example;
import java.time.LocalDate;
import org.json.simple.JSONObject;

import jakarta.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double amount;
    private String category;
    private String type;
    private String userId;

     // Default constructor (required by JPA)
     public Transaction() { }
    // Constructor
    public Transaction(LocalDate date, double amount, String category, String type, String userId) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.userId = userId;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public String getUserId() { return userId; }

  
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("date", date.toString());
        json.put("amount", amount);
        json.put("category", category);
        json.put("type", type);
        json.put("userId", userId);
        return json;
    }

    @Override
    public String toString() {
        return "Transaction [date=" + date + ", amount=" + amount + ", category=" + category + ", type=" + type + ", userId=" + userId + "]";
    }
}
