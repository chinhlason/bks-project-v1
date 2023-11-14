package com.bksproject.bksproject.Repository;

import com.bksproject.bksproject.Model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Long> {
    Courses findCoursesById(Long id);

    Optional<Courses> findCoursesBySerial(String serial);

    Boolean existsBySerial(String serial);


    @Query(value = "SELECT * FROM courses WHERE serial LIKE :type%", nativeQuery = true)
    List<Courses> findAllCoursesBySerialType(@Param("type") String type);

    @Query(value = "SELECT c FROM Courses c WHERE c.serial LIKE :type%",
            countQuery = "SELECT COUNT(c) FROM Courses c WHERE c.serial LIKE :type",
            nativeQuery = false)
    Page<Courses> findCoursesBySerialType(@Param("type") String type, Pageable pageable);
}
