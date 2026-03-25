package com.quizapp.quizengine.repository;

import com.quizapp.quizengine.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByUserId(Long userId);
    List<Attempt> findByUserIdAndQuizId(Long userId, Long quizId);
}