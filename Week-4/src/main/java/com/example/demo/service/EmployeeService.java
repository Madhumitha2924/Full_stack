package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    public List<String> getAllEmployees() {
        return Arrays.asList("John", "David", "Madhu");
    }
}