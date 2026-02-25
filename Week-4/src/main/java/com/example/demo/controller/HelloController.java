package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.GreetingService;

@RestController
public class HelloController {

    @Autowired   // Field Injection
    private GreetingService greetingService;

    @GetMapping("/greet")
    public String greet() {
        return greetingService.getMessage();
    }
}