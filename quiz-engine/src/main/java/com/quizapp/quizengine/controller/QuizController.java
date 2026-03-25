package com.quizapp.quizengine.controller;

import com.quizapp.quizengine.dto.QuestionRequest;
import com.quizapp.quizengine.dto.QuizRequest;
import com.quizapp.quizengine.dto.QuizSubmitRequest;
import com.quizapp.quizengine.model.*;
import com.quizapp.quizengine.service.QuizService;
import com.quizapp.quizengine.repository.AttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private AttemptRepository attemptRepository;

    // Create quiz
    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(
            @RequestBody QuizRequest request) {
        return ResponseEntity.ok(quizService.createQuiz(request));
    }

    // Get all quizzes
    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    // Get quiz by ID
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(
            @PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    // Add question
    @PostMapping("/question/add")
    public ResponseEntity<Question> addQuestion(
            @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(quizService.addQuestion(request));
    }

    // Get questions by quiz ID
    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<Question>> getQuestions(
            @PathVariable Long quizId) {
        return ResponseEntity.ok(
            quizService.getQuestionsByQuizId(quizId));
    }

    // Submit quiz
    @PostMapping("/submit")
    public ResponseEntity<Attempt> submitQuiz(
            @RequestBody QuizSubmitRequest request) {
        return ResponseEntity.ok(quizService.submitQuiz(request));
    }

    // Get attempts by user ID
    @GetMapping("/attempts/{userId}")
    public ResponseEntity<List<Attempt>> getAttemptsByUser(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
            attemptRepository.findByUserId(userId));
    }
}