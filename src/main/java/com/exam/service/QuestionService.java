package com.exam.service;

import com.exam.exception.QuestionNotFoundException;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;

import java.util.Set;

public interface QuestionService {
    public Question addQuestion(Question question);
    public Question updateQuestion(Question question);
    public Set<Question> getQuestions();
    public  Question getQuestion(Long questionId) throws QuestionNotFoundException;
    public void deleteQuestion(Long questionId);
    public Set<Question> getQuestionOfQuiz(Quiz quiz);
    public Question get(Long questionId);
}
