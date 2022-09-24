package com.exam.controller;

import com.exam.model.User;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Result;
import com.exam.repo.QuizRepository;
import com.exam.repo.ResultRepository;
import com.exam.repo.UserRepository;
import com.exam.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/result")
public class ResultController {
    @Autowired
    ResultRepository resultRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    QuizRepository quizRepository;
    @Autowired
    ResultService resultService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Result>> getResultOfUser(@PathVariable("userId") Long userId){
        Optional<User> user = userRepository.findById(userId);
        return ResponseEntity.ok(this.resultService.getResult(user));
    }
    @GetMapping("/getUsers/{quizId}")
    public ResponseEntity<List<Result>> getUsersByQuiz(@PathVariable("quizId") Long quizId){
        Optional<Quiz> quiz = quizRepository.findById(quizId);

        return ResponseEntity.ok(this.resultService.getUsersByQuiz(quiz));
    }
}
