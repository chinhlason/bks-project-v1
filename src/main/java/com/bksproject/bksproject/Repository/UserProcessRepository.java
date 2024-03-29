package com.bksproject.bksproject.Repository;

import com.bksproject.bksproject.Model.Purchase;
import com.bksproject.bksproject.Model.UserProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProcessRepository extends JpaRepository<UserProcess, Long> {
    @Query(value = "SELECT * FROM user_process WHERE lesson_id = :id", nativeQuery = true)
    UserProcess findByLessonId(@Param("id")Long id);

    @Query(value = "SELECT * FROM user_process WHERE user_id = :userId AND lesson_id = :lessonId LIMIT 1", nativeQuery = true)
    UserProcess findUserProcessByUserIdAndLessonId(@Param("userId") Long userId, @Param("lessonId") Long lessonId);
}
