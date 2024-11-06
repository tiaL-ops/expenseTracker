package com.example;


import javax.persistence.*;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "User")
public class User {
    
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String userid; 

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @CreationTimestamp
    private LocalDate createdDate;

    @UpdateTimestamp
    private LocalDate updatedDate;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(String userid, String password, String email) {
        this.userid = userid;
        this.password = password;
        this.email = email;
    }

    // Getters
    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    // Setters
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
