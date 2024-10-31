package com.example;
//package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class ExpenseController {
    

@RestController
@RequestMapping("/api")
public class DefaultController {

    @GetMapping("/test")
    public String testEndpoint() {
        return "Backend is working!";
    }
}

}
