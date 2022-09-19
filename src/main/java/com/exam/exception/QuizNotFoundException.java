package com.exam.exception;

public class QuizNotFoundException extends Exception{
    public QuizNotFoundException() {
        super("Quiz not found.");
    }

    public QuizNotFoundException(String msg)
    {
        super(msg);
    }
}
