package com.example.rest.repository;

import com.example.rest.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    File save(File file);

    @Query(value = "SELECT * FROM file WHERE file.post_id = :postId AND file.deleted = false", nativeQuery = true)
    List<File> findByPostId(int postId);

    @Query(value = "SELECT * FROM file WHERE file.deleted = false", nativeQuery = true)
    List<File> findAllFiles();
}
