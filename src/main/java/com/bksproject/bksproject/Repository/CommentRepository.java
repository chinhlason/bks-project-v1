package com.bksproject.bksproject.Repository;

import com.bksproject.bksproject.Model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findById(Long id);
//    Optional<Comments> findByUserId(Long id);
//    Optional<Comments> findByPostId(Long id);

}
