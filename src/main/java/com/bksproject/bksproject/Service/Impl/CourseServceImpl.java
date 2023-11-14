package com.bksproject.bksproject.Service.Impl;

import com.bksproject.bksproject.Model.Courses;
import com.bksproject.bksproject.Repository.CourseRepository;
import com.bksproject.bksproject.Service.ICourseService;
import com.bksproject.bksproject.payload.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServceImpl implements ICourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Courses> findAllCourseByType(Pageable pageable, String type) {
        List<Courses> result = new ArrayList<>();
        return courseRepository.findCoursesBySerialType(type, pageable).getContent();
    }

}
