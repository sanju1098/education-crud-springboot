package com.education.exception;

public class StudentNotCreated extends RuntimeException{
    public StudentNotCreated(String message) {
        super(message);
    }
}