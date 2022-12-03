package com.example.rest.repository;

import com.example.rest.model.entity.Report;
import com.example.rest.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
    Report save(Report report);

    @Query(value = "SELECT * FROM report WHERE report.user_id  = :userId AND report.post_id = :postId  AND report.deleted = false ", nativeQuery = true)
    Report findByUserIdAndPostId(int userId, int postId);
}
