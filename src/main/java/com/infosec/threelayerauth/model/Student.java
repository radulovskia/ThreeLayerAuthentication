package com.infosec.threelayerauth.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student{
    public Student(String name, String email, String password, StudentType type, List<Course> courses, LocalDate enrollmentDate){
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.courses = courses;
        this.enrollmentDate = enrollmentDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate enrollmentDate;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private StudentType type;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Course> courses;
}
