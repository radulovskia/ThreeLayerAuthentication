package com.infosec.threelayerauth.service;

import com.infosec.threelayerauth.model.Student;
import com.infosec.threelayerauth.model.StudentType;

import java.time.LocalDate;
import java.util.List;

public interface StudentService{
    List<Student> listAll();
    Student findById(Long id);
    Student create(String name, String email, String password, StudentType type, List<Long> courseId, LocalDate enrollmentDate);
    Student update(Long id, String name, String email, String password, StudentType type, List<Long> coursesId, LocalDate enrollmentDate);
    Student delete(Long id);
    List<Student> filter(Long courseId, Integer yearsOfStudying);
}
