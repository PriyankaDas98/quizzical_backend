package com.exam.controller;

import com.exam.exception.QuizNotFoundException;
import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
//@CrossOrigin("*")
@CrossOrigin("http://localhost:4200")
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizService quizService;

    //add
    @PostMapping("/")
    public ResponseEntity<Quiz> add(@Valid @RequestBody Quiz quiz){
        return ResponseEntity.ok(this.quizService.addQuiz(quiz));
    }

    //update
    @PutMapping("/")
    public ResponseEntity<Quiz> update(@Valid @RequestBody Quiz quiz){
        return ResponseEntity.ok(this.quizService.updateQuiz(quiz));
    }

    //get quiz
    @GetMapping("/")
    public ResponseEntity<Set<Quiz>>quizzes(){
        return ResponseEntity.ok(this.quizService.getQuizzes());
    }

    //single quiz
    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> quiz(@PathVariable("quizId") Long quizId) throws QuizNotFoundException {
        return ResponseEntity.ok(this.quizService.getQuiz(quizId));
    }

    //delete
    @DeleteMapping("/{quizId}")
    public void delete(@PathVariable("quizId") Long quizId){

        this.quizService.deleteQuiz(quizId);
    }

    @GetMapping("/category/{cid}")
    public List<Quiz> getQuizzesOfCategory(@PathVariable("cid") Long cid){
        Category category = new Category();
        category.setCid(cid);
        return this.quizService.getQuizzesOfCategory(category);
    }
    //get active quizzes
    @GetMapping("/active")
    public List<Quiz> getActiveQuizzes() {
        return this.quizService.getActiveQuizzes();
    }

    //get active quizzes of category
    @GetMapping("/category/active/{cid}")
    public List<Quiz> getActiveQuizzes(@PathVariable("cid") Long cid) {
        Category category = new Category();
        category.setCid(cid);
        return this.quizService.getActiveQuizzesOfCategory(category);
    }


}
