package com.example;

import javax.annotation.processing.Generated;

import java.util.List;
import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "users")  
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    @Column(name = "username", nullable = false, unique = true)
    private String username; 

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    
    public User() {}

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Date getCreated() { return created; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
}
