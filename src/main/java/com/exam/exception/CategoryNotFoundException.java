package com.exam.exception;

public class CategoryNotFoundException extends Exception {

    public CategoryNotFoundException() {
        super("Category with this username not found in database !!");
    }

    public CategoryNotFoundException(String msg) {
        super(msg);
    }
}
