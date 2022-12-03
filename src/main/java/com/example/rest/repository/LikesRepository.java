package com.example.rest.repository;

import com.example.rest.model.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {
    List<Likes> findByPostId(int postId);

    //xem userId đã like bài postId chưa
    @Query(value = "SELECT * " +
            "FROM likes " +
            "WHERE likes.post_id = :postId " +
            "AND likes.user_id = :userId "
            , nativeQuery = true)
    Likes findByUserIdAndPostId(int userId, int postId);

    @Query(value = "INSERT INTO likes(likes.deleted, likes.post_id, likes.user_id) " +
            "VALUES (:isDeleted, :postId, :userId)"
            , nativeQuery = true)
    void saveLike(String postId, String userId, boolean isDeleted);

    Likes save(Likes like);

    @Query(value = "UPDATE likes " +
            "SET likes.deleted= :isDeleted " +
            "WHERE likes.post_id = :postId " +
            "AND likes.user_id = :userId " +
            "AND likes.deleted= false"
            , nativeQuery = true)
    void updateLike(String postId, String userId, boolean isDeleted);

    @Query(value = "SELECT * " +
            "FROM likes " +
            "WHERE likes.post_id = :postId " +
            "AND likes.deleted= false"
            , nativeQuery = true)
    List<Likes> findByPostId(String postId);

    @Query(value = "SELECT * " +
            "FROM likes " +
            "WHERE likes.deleted= false"
            , nativeQuery = true)
    List<Likes> findAllLikes();
}
