package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class ExpenseController {

    private final ExpenseTracker expenseTracker;
    private final TransactionRepository transactionRepository;

    @Autowired
    public ExpenseController(ExpenseTracker expenseTracker,TransactionRepository transactionRepository) {
        this.expenseTracker = expenseTracker;
        this.transactionRepository = transactionRepository;
    }
    @GetMapping("/test")
    public String testendpoint() {
        return "Backend  working";
        
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
public void createTransaction(@RequestParam User user, @RequestBody Transaction transaction) {
    if ("income".equalsIgnoreCase(transaction.getType())) {
        expenseTracker.addIncome(transaction.getDate(), transaction.getAmount(), transaction.getCategory(),transaction.getUserId());
    } else {
        expenseTracker.addExpense(transaction.getDate(), transaction.getAmount(), transaction.getCategory(),transaction.getUserId());
    }
}
@GetMapping("/transactions")
public List<Transaction> getTransactions() {
    return expenseTracker.getTransactions();
}

@GetMapping("/test-direct-save")
    public String testDirectSave() {
        User user= new User();
        user.setUsername("helloTest");
        user.setEmail("test@gmail.com");
        user.setPassword("passWord");
    
        Transaction transaction = new Transaction(LocalDate.now(), 100.0, "test_category", "income",user);
        transactionRepository.save(transaction);
        return "Direct transaction saved!";
    }




}
