package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class ExpenseController {

    private final ExpenseTracker expenseTracker;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public ExpenseController(ExpenseTracker expenseTracker, TransactionRepository transactionRepository) {
        this.expenseTracker = expenseTracker;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Backend working";
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
        System.out.println("Endpoint invoked!");
        User user = new User();
        user.setUsername("helloTest1");
        user.setEmail("test1@gmail.com");
        user.setPassword("passWord");
    
       
        userRepository.save(user);
    
        
        Transaction transaction = new Transaction(LocalDate.now(), 100.0, "test_category", "income", user);
    
      
        transactionRepository.save(transaction);
        return "Direct transaction saved!";
    }
    
}
