package com.quizapp.quizengine.repository;

import com.quizapp.quizengine.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByTopic(String topic);
}