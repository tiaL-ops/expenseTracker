package com.example;
import com.example.LoginRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class ExpenseController {

    private final ExpenseTracker expenseTracker;
    private final TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private JwtService jwtService; 

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

@GetMapping("/test-direct-save")
    public String testDirectSave() {
        Transaction transaction = new Transaction(LocalDate.now(), 100.0, "test_category", "income", "test_user");
        transactionRepository.save(transaction);
        return "Direct transaction saved!";
    }
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUserId(loginRequest.getUserid());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            String token = jwtService.generateToken(user); 
            return ResponseEntity.ok(new AuthResponse(token)); 
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"); 
    }
    
    @GetMapping("/api/transactions")
    public ResponseEntity<?> getUserTransactions(@RequestHeader("Authorization") String token) {
        String userId = jwtService.extractUserId(token); // Extract user ID from token
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return ResponseEntity.ok(transactions);
    }
    
}
