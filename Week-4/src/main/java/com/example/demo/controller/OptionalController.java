package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ExtraService;

@RestController
public class OptionalController {

    @Autowired(required = false)
    private ExtraService extraService;

    @GetMapping("/optional")
    public String checkOptional() {

        if (extraService != null) {
            return extraService.extra();
        } else {
            return "Extra Service is NOT Available";
        }
    }
}