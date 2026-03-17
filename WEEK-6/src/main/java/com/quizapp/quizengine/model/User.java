package com.quizapp.quizengine.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class User {

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Must be a valid email address")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    private String password;
}