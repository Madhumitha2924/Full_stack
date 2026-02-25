package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("message", "Welcome to Spring MVC Task 4.7");
        return "home";
    }
}