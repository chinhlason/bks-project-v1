package com.bksproject.bksproject.Service;

import com.bksproject.bksproject.Model.Courses;
import com.bksproject.bksproject.payload.response.PostResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICourseService {
    List<Courses> findAllCourseByType(Pageable pageable, String type);
}
