package com.exam.controller;

import com.exam.exception.QuestionNotFoundException;
import com.exam.exception.QuizNotFoundException;
import com.exam.model.User;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Result;
import com.exam.repo.UserRepository;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import com.exam.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin("*")
//@CrossOrigin("http://localhost:4200")
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    UserRepository userRepository;
    //result
    @Autowired
    ResultService resultService;
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
    @PostMapping("/eval-quiz/{userId}")
    public ResponseEntity<?> evalQuiz(@PathVariable("userId") Long userId,
                                          @RequestBody List<Question> questions){
        double marksGot = 0;
        int correctAnswers = 0;
        int attempted = 0;

        for(Question q : questions){
            //single question details
            Question question = this.questionService.get(q.getQuesId());
            if(question.getAnswer().equals(q.getGivenAnswer())){
                //correct
                correctAnswers++;
                //calculating marks of a single question (max marks of quiz / no. of questions
                double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
                marksGot += marksSingle;
            } if (q.getGivenAnswer()!=null) {
                attempted++;
            }
        }
        //storing the result
        Result res = new Result();
        res.setMarksGot(marksGot);
        res.setCorrectAnswers(correctAnswers);
        res.setAttempted(attempted);
        res.setQuiz(questions.get(0).getQuiz());
        res.setUser(userRepository.getOne(userId));

        resultService.add(res);

        Map<String, Object> map = Map.of("marksGot", marksGot,
                "correctAnswers", correctAnswers,
                "attempted",attempted);
        return ResponseEntity.ok(map);

    }
}
