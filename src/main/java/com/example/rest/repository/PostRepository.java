package com.example.rest.repository;

import com.example.rest.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    @Query(value = "SELECT * FROM post WHERE post.id = :id AND post.deleted = false", nativeQuery = true)
    Post findById(int id);

    Post save(Post post);

    @Query(value = "SELECT * FROM post WHERE post.id > :lastId AND post.deleted = false"
            , nativeQuery = true)
    List<Post> findNewPosts(@Param("lastId") String lastId);
}
