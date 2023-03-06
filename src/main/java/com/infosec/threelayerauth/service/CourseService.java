package com.infosec.threelayerauth.service;

import com.infosec.threelayerauth.model.Course;

import java.util.List;

public interface CourseService{
    Course findById(Long id);
    List<Course> listAll();
    Course create(String name);
}
