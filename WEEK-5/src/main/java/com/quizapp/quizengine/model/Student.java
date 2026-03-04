package com.quizapp.quizengine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity                         // Tells JPA: this class = a DB table
@Table(name = "students")       // Table name in MySQL will be "students"
public class Student {

    @Id                                                      // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)      // Auto increment
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "age")
    private int age;

    // ── Default Constructor (REQUIRED by JPA) ──
    public Student() {}

    // ── Parameterized Constructor ──
    public Student(String firstName, String lastName,
                   String email, String department, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.age = age;
    }

    // ── Getters ──
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public int getAge() { return age; }

    // ── Setters ──
    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setDepartment(String department) { this.department = department; }
    public void setAge(int age) { this.age = age; }

    // ── toString ──
    @Override
    public String toString() {
        return "Student{id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", department='" + department + '\'' +
               ", age=" + age + '}';
    }
}