package com.exam.repo;

import com.exam.model.User;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result,Long> {

    public List<Result> findByUser(Optional<User> user);
    public List<Result> findByQuiz(Optional<Quiz> quiz);

}
