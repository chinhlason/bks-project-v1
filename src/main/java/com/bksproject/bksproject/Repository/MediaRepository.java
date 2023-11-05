package com.bksproject.bksproject.Repository;

import com.bksproject.bksproject.Model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    Optional<Media> findMediaById(Long id);

    @Query(value = "SELECT * FROM media WHERE lesson_id = :id", nativeQuery = true)
    Media findMediaByLessonId(@Param("id")Long id);
}
