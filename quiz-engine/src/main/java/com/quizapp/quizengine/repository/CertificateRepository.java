package com.quizapp.quizengine.repository;

import com.quizapp.quizengine.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findByUserIdAndQuizId(Long userId, Long quizId);
    List<Certificate> findByUserId(Long userId);
}