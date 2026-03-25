package com.quizapp.quizengine.service;

import com.quizapp.quizengine.dto.QuestionRequest;
import com.quizapp.quizengine.dto.QuizRequest;
import com.quizapp.quizengine.dto.QuizSubmitRequest;
import com.quizapp.quizengine.model.*;
import com.quizapp.quizengine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new quiz
    public Quiz createQuiz(QuizRequest request) {
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setTopic(request.getTopic());
        quiz.setTimeLimit(request.getTimeLimit());
        quiz.setPassingScore(request.getPassingScore());
        return quizRepository.save(quiz);
    }

    // Get all quizzes
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // Get quiz by ID
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found!"));
    }

    // Add question to quiz
    public Question addQuestion(QuestionRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found!"));

        Question question = new Question();
        question.setQuiz(quiz);
        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectAnswer(request.getCorrectAnswer());

        return questionRepository.save(question);
    }

    // Get questions by quiz ID
    public List<Question> getQuestionsByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    // Submit quiz and calculate score
    public Attempt submitQuiz(QuizSubmitRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found!"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        List<Question> questions = questionRepository.findByQuizId(request.getQuizId());
        Map<Long, String> answers = request.getAnswers();

        // Calculate score
        int correct = 0;
        for (Question q : questions) {
            String submitted = answers.get(q.getId());
            if (submitted != null && submitted.equals(q.getCorrectAnswer())) {
                correct++;
            }
        }

        int score = (int) ((correct * 100.0) / questions.size());
        boolean passed = score >= quiz.getPassingScore();

        Attempt attempt = new Attempt();
        attempt.setUser(user);
        attempt.setQuiz(quiz);
        attempt.setScore(score);
        attempt.setPassed(passed);
        attempt.setAttemptDate(LocalDateTime.now());

        return attemptRepository.save(attempt);
    }
}