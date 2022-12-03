package com.example.rest.repository;

import com.example.rest.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Query(value = "SELECT * FROM comment WHERE comment.post_id = :postId AND comment.deleted = false ", nativeQuery = true)
    List<Comment> findByPostId(int postId); // tra ra danh sanh comment post id

    Comment save(Comment comment);


}
