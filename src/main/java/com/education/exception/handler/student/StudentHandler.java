package com.education.exception.handler.student;

import com.education.exception.StudentIdNotFound;
import com.education.exception.StudentNotCreated;
import com.education.exception.StudentNotUpdated;
import com.education.exception.response.student.StudentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice // to show custom response
public class StudentHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({StudentNotCreated.class, StudentNotUpdated.class})
    public ResponseEntity<StudentResponse> studentNotCreatedHandler(StudentNotCreated ex){

        StudentResponse studentCreateErrorResponse =  StudentResponse.
                builder().
                message(ex.getMessage()).
                code(HttpStatus.BAD_REQUEST.value()).
                httpStatus(HttpStatus.BAD_REQUEST).
                build();

        return new ResponseEntity<>(studentCreateErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StudentIdNotFound.class)
    public ResponseEntity<StudentResponse> studentInfoNotFoundById(StudentIdNotFound ex){

        StudentResponse resourceNotFoundResponse =  StudentResponse.
                builder().
                message(ex.getMessage()).
                code(HttpStatus.NOT_FOUND.value()).
                httpStatus(HttpStatus.NOT_FOUND).
                build();

        return  ResponseEntity.ok(resourceNotFoundResponse);
    }

}
