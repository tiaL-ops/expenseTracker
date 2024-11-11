package com.example;
import java.time.LocalDate;
import org.json.simple.JSONObject;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Double amount;  

    private String category;
    
    private String type; 

    @ManyToOne
    @JoinColumn(name = "user_id",nullable =false)
    private User user;


     // Default constructor (required by JPA)
     public Transaction() { }


    // Constructor
    public Transaction(LocalDate date, double amount, String category, String type, User user) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.user = user;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public User getUserId() { return user; }

  
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("date", date.toString());
        json.put("amount", amount);
        json.put("category", category);
        json.put("type", type);
        json.put("userId", user.getId());
        return json;
    }

    @Override
    public String toString() {
        return "Transaction [date=" + date + ", amount=" + amount + ", category=" + category + ", type=" + type + ", userId=" + user.username + "]";
    }
}
