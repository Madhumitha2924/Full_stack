package com.quizapp.quizengine.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private int timeLimit; // in minutes

    @Column(nullable = false)
    private int passingScore; // minimum score to pass (e.g. 60)

    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;
}