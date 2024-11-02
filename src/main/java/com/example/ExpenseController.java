package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private final ExpenseTracker expenseTracker;

    @Autowired
    public ExpenseController(ExpenseTracker expenseTracker) {
        this.expenseTracker = expenseTracker;
    }
    @GetMapping("/test")
    public String testendpoint() {
        return "Backend hello  working";
        
    }

    @PostMapping("/transactions")
public void createTransaction(@RequestParam String userId, @RequestBody Transaction transaction) {
    if ("income".equalsIgnoreCase(transaction.getType())) {
        expenseTracker.addIncome(userId, transaction.getDate(), transaction.getAmount(), transaction.getCategory());
    } else {
        expenseTracker.addExpense(userId, transaction.getDate(), transaction.getAmount(), transaction.getCategory());
    }
}

}
