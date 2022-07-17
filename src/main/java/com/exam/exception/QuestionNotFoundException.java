package com.exam.exception;

public class QuestionNotFoundException extends Exception{
	 public QuestionNotFoundException() {
	        super("Question not found.");
	    }

	    public QuestionNotFoundException(String msg)
	    {
	        super(msg);
	    }
}
