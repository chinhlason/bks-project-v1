package com.bksproject.bksproject.Repository;

import com.bksproject.bksproject.Model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {
    Optional<Posts> findByCategory(String category);
//    Optional<Posts> findByUserID(Long userID);
    Optional<Posts> findByTitle(String title);

    Optional<Posts> findById(Long id);

}
