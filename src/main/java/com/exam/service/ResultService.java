package com.exam.service;

import com.exam.model.User;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Result;

import java.util.List;
import java.util.Optional;

public interface ResultService {
    //fetch result by user id
    public List<Result> getResult(Optional<User> user);
    //add result to db
    public Result add(Result result);

    //get users that attempt a quiz
    public List<Result> getUsersByQuiz(Optional<Quiz> quiz);


}
