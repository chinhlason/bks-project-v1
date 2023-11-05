package com.bksproject.bksproject.Repository;

import com.bksproject.bksproject.Model.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lessons, Long> {
    Lessons findLessonsById(Long id);


    @Query(value = "SELECT * FROM lessons WHERE course_id = :id", nativeQuery = true)
    List<Lessons> findAllByCourseId(@Param("id")Long id);
}
