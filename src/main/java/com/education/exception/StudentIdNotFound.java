package com.education.exception;

public class StudentIdNotFound extends RuntimeException{
    public StudentIdNotFound(String message) {
        super(message);
    }
}
