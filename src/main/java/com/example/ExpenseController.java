package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private final ExpenseTracker expenseTracker;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public ExpenseController(ExpenseTracker expenseTracker, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.expenseTracker = expenseTracker;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Backend working");
    }

    @PostMapping("/transactions")
    public ResponseEntity<String> createTransaction(@RequestBody @Valid Transaction transaction) {
        if ("income".equalsIgnoreCase(transaction.getType())) {
            expenseTracker.addIncome(transaction.getDate(), transaction.getAmount(), transaction.getCategory(), transaction.getUserId());
        } else {
            expenseTracker.addExpense(transaction.getDate(), transaction.getAmount(), transaction.getCategory(), transaction.getUserId());
        }
        return ResponseEntity.status(201).body("Transaction created successfully");
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions() {
        List<Transaction> transactions = expenseTracker.getTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/test-direct-save")
    public ResponseEntity<String> testDirectSave() {
        User user = new User();
        user.setUsername("helloTest1");
        user.setEmail("test1@gmail.com");
        user.setPassword("passWord");
        userRepository.save(user);

        Transaction transaction = new Transaction(LocalDate.now(), 100.0, "test_category", "income", user);
        transactionRepository.save(transaction);

        return ResponseEntity.status(201).body("Direct transaction saved!");
    }
}
