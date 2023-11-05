package com.bksproject.bksproject.Repository;

import com.bksproject.bksproject.Model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Long> {
    Courses findCoursesById(Long id);

    Optional<Courses> findCoursesBySerial(String serial);

    Boolean existsBySerial(String serial);
}
