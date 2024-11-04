package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class ExpenseController {

    private final ExpenseTracker expenseTracker;

    @Autowired
    public ExpenseController(ExpenseTracker expenseTracker) {
        this.expenseTracker = expenseTracker;
    }
    @GetMapping("/test")
    public String testendpoint() {
        return "Backend  working";
        
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
public void createTransaction(@RequestParam String userId, @RequestBody Transaction transaction) {
    if ("income".equalsIgnoreCase(transaction.getType())) {
        expenseTracker.addIncome(userId, transaction.getDate(), transaction.getAmount(), transaction.getCategory());
    } else {
        expenseTracker.addExpense(userId, transaction.getDate(), transaction.getAmount(), transaction.getCategory());
    }
}
@GetMapping("/transactions")
public List<Transaction> getTransactions() {
    return expenseTracker.getTransactions();
}

@GetMapping("/test-save")
public String testSaveTransaction() {
    expenseTracker.testSaveTransaction();
    return "Test transaction saved!";
}




}
