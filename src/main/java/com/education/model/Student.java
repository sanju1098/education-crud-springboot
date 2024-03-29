package com.education.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id // to make id as PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_id", nullable = false)
    private String emailId;

    @Column(name = "dob", nullable = false)
    private String dob;

    @Column(name = "phone_num", nullable = false)
    private String phoneNumber;
}
