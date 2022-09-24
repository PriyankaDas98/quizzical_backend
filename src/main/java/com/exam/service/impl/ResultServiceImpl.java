package com.exam.service.impl;

import com.exam.model.User;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Result;
import com.exam.repo.ResultRepository;
import com.exam.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    ResultRepository resultRepository;
    @Override
    public List<Result> getResult(Optional<User> user) {

        return resultRepository.findByUser(user);
    }

    @Override
    public Result add(Result result) {
        return resultRepository.save(result);

    }

    @Override
    public List<Result> getUsersByQuiz(Optional<Quiz> quiz) {
        return resultRepository.findByQuiz(quiz);
    }

}
