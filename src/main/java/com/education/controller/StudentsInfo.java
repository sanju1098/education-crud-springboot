package com.education.controller;

import com.education.exception.StudentIdNotFound;
import com.education.exception.StudentNotCreated;
import com.education.exception.StudentNotUpdated;
import com.education.model.Student;
import com.education.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/education")

public class StudentsInfo {

    @Autowired
    private StudentRepo studentRepo;

    // Validation for CREATING Students:
    private void validateStudent(Student student) {
        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
            throw new StudentNotCreated("First name can't be null or empty");
        }

        if (student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            throw new StudentNotCreated("Last name can't be null or empty");
        }

        if (student.getEmailId() == null || student.getEmailId().trim().isEmpty()) {
            throw new StudentNotCreated("Email id can't be null or empty");
        } else {
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(student.getEmailId());
            if (!matcher.matches()) {
                throw new StudentNotCreated("Invalid email format");
            }
        }
        if (student.getPhoneNumber() == null || student.getPhoneNumber().trim().isEmpty()) {
            throw new StudentNotCreated("Phone number can't be null or empty");
        }
    }



    // GET student details
    @GetMapping("/students")
    public List<Student> getStudentInfo() throws SQLException {
        return studentRepo.findAll();
    }

    // CREATE students details
    @PostMapping("/student/create")
    public Student createStudent(@RequestBody Student student){
        try{
            validateStudent(student); // Validate student details
            return studentRepo.save(student);
        }catch (DataAccessException ex){
            // if any other error
            throw new StudentNotCreated("Student Creation Failed.");
        }
    }

    // UPDATE student details
    @PutMapping("/student/{id}/update")
    public ResponseEntity<Student> updateStudentInfo(@PathVariable long id, @RequestBody Student studentInfo){
        Student updateStudent =  studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotUpdated("Student "+ studentInfo.getFirstName() +" is not found in Records..!"));

        updateStudent.setFirstName(studentInfo.getFirstName());
        updateStudent.setLastName(studentInfo.getLastName());
        updateStudent.setEmailId(studentInfo.getEmailId());
        updateStudent.setDob(studentInfo.getDob());
        updateStudent.setPhoneNumber(studentInfo.getPhoneNumber());

        validateStudent(updateStudent);
        studentRepo.save(updateStudent);

        return  ResponseEntity.ok(studentInfo);
    }

    // GET by Student ID
    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentInfoById(@PathVariable long id, Student student){
        Student studentInfo =  studentRepo.findById(id)
                .orElseThrow(() -> new StudentIdNotFound("Student "+ student.getFirstName() +" is not found in Records..!"));

        return ResponseEntity.ok(studentInfo);
    }

    // DELETE Student Info
    @DeleteMapping("/student/delete/{id}")
    public ResponseEntity<Object> deleteStudentInfo(@PathVariable long id){
        Student studentInfo =  studentRepo.findById(id)
                .orElseThrow(() -> new StudentIdNotFound("Student "+ id +" is not found in Records..!"));

        studentRepo.delete(studentInfo);

        int StatusCode = 200;
        String message = "Student with ID " + id + " has been deleted successfully";
        Map<String,String> response = new HashMap<>();
        response.put("code", String.valueOf(StatusCode));
        response.put("message", message);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
