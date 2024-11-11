package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;



@SpringBootApplication
@EntityScan(basePackages = "com.example")
public class ExpenseTrackerApp {
    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApp.class, args);
            System.out.println("test");
    }
}
