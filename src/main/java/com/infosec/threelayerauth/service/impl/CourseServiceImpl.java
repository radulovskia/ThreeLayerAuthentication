package com.infosec.threelayerauth.service.impl;

import com.infosec.threelayerauth.model.Course;
import com.infosec.threelayerauth.model.exceptions.InvalidCourseIdException;
import com.infosec.threelayerauth.repository.CourseRepository;
import com.infosec.threelayerauth.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @Override
    public Course findById(Long id){
        return courseRepository.findById(id).orElseThrow(InvalidCourseIdException::new);
    }

    @Override
    public List<Course> listAll(){
        return courseRepository.findAll();
    }

    @Override
    public Course create(String name){
        Course course = new Course(name);
        return courseRepository.save(course);
    }
}