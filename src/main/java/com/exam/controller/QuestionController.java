package com.exam.controller;

import com.exam.exception.QuestionNotFoundException;
import com.exam.exception.QuizNotFoundException;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
//@CrossOrigin("*")
@CrossOrigin("http://localhost:4200")
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;
    //private Object service;

    //add question
    @PostMapping("/")
    public ResponseEntity<Question> add(@Valid @RequestBody Question question){
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }

    //update
    @PutMapping("/")
    public ResponseEntity<Question>update(@Valid @RequestBody Question question){
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    //get all question of any qid
    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("qid") Long qid) throws QuizNotFoundException {
        // send numberOfQuestion number of questions not all
        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Question> questions = quiz.getQuestions();
        List<Question> list = new ArrayList(questions);
        if(list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
            list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions() + 1));
        }
        list.forEach((q)->{
            q.setAnswer("");
        });
        Collections.shuffle(list);
        return ResponseEntity.ok(list);
    }
    //Admin
    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getQuestionOfQuizAdmin(@PathVariable("qid") Long qid){
        Quiz quiz = new Quiz();
        quiz.setQid(qid);
        Set<Question> QuestionsOfQuiz = this.questionService.getQuestionOfQuiz(quiz);
        return ResponseEntity.ok(QuestionsOfQuiz);
    }

    //get single question
    @GetMapping("/{quesId}")
    public Question get(@PathVariable("quesId") Long qid) throws QuestionNotFoundException{
        return this.questionService.getQuestion(qid);

    }

    //delete
    @DeleteMapping("/{quesId}")
    public void delete(@PathVariable("quesId") Long quesId){
        this.questionService.deleteQuestion(quesId);
    }

    //evaluate quiz
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions){
        System.out.println(questions);
        double marksGot = 0;
        int correctAnswers = 0;
        int attempted = 0;

        for(Question q : questions){
            //single question
            Question question = this.questionService.get(q.getQuesId());
            if(question.getAnswer().equals(q.getGivenAnswer())){
                //correct
                correctAnswers++;
                double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
                marksGot += marksSingle;
            } if (q.getGivenAnswer()!=null) {
                attempted++;
            }
        }
        Map<String, Object> map = Map.of("marksGot", marksGot,
                "correctAnswers", correctAnswers,
                "attempted",attempted);
        return ResponseEntity.ok(map);

    }
}
