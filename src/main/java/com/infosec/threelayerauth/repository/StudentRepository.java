package com.infosec.threelayerauth.repository;

import com.infosec.threelayerauth.model.Course;
import com.infosec.threelayerauth.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    Student findByEmail(String email);
    List<Student> findAllByCoursesContainingAndEnrollmentDateBefore(Course course, LocalDate localDate);
    List<Student> findAllByCoursesContaining(Course course);
    List<Student> findAllByEnrollmentDateBefore(LocalDate localDate);
}
