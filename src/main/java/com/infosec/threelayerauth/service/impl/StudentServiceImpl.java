package com.infosec.threelayerauth.service.impl;

import com.infosec.threelayerauth.model.Course;
import com.infosec.threelayerauth.model.Student;
import com.infosec.threelayerauth.model.StudentType;
import com.infosec.threelayerauth.model.exceptions.InvalidStudentIdException;
import com.infosec.threelayerauth.repository.CourseRepository;
import com.infosec.threelayerauth.repository.StudentRepository;
import com.infosec.threelayerauth.service.StudentService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService, UserDetailsService{
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder,
                              CourseRepository courseRepository){
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Student> listAll(){
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id){
        return studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
    }

    @Override
    public Student create(String name, String email, String password, StudentType type, List<Long> courseId, LocalDate enrollmentDate){
        String encPass = passwordEncoder.encode(password);
        List<Course> courses = courseRepository.findAllById(courseId);
        Student student = new Student(name,email,encPass,type,courses,enrollmentDate);
        return studentRepository.save(student);
    }

    @Override
    public Student update(Long id, String name, String email, String password, StudentType type, List<Long> coursesId, LocalDate enrollmentDate){
        Student student = studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
        student.setName(name);
        student.setEmail(email);
        student.setPassword(passwordEncoder.encode(password));
        student.setType(type);
        student.setCourses(courseRepository.findAllById(coursesId));
        student.setEnrollmentDate(enrollmentDate);
        return studentRepository.save(student);
    }

    @Override
    public Student delete(Long id){
        Student student = studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
        studentRepository.delete(student);
        return student;
    }

    @Override
    public List<Student> filter(Long courseId, Integer yearsOfStudying){
        List<Student> students;
        if(courseId != null && yearsOfStudying != null){
            Course course = courseRepository.getById(courseId);
            LocalDate localDate = LocalDate.now().minusYears(yearsOfStudying);
            students = studentRepository.findAllByCoursesContainingAndEnrollmentDateBefore(course, localDate);
        } else if(courseId != null){
            Course course = courseRepository.getById(courseId);
            students = studentRepository.findAllByCoursesContaining(course);
        } else if(yearsOfStudying != null){
            LocalDate localDate = LocalDate.now().minusYears(yearsOfStudying);
            students = studentRepository.findAllByEnrollmentDateBefore(localDate);
        } else
            students = studentRepository.findAll();
        return students;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Student student = studentRepository.findByEmail(username);
        if(student == null)
            throw new UsernameNotFoundException("Username not found");
//        return new User(student.getEmail(), student.getPassword(),
//                Stream.of(new SimpleGrantedAuthority(String.format("ROLE_%S", student.getType()))).
//                        collect(Collectors.toList()));
        return User.builder().username(student.getEmail())
                .password(student.getPassword())
                .roles(student.getType().toString()).build();
    }
}