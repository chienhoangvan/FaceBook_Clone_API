package com.example.rest.repository;

import com.example.rest.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM user WHERE user.phone_number = :phoneNumber AND user.deleted = false", nativeQuery = true)
    User findByPhoneNumber(String phoneNumber);
    User save(User user);
    @Query(value = "SELECT * FROM user WHERE user.phone_number = :phoneNumber AND user.password = :password AND user.deleted = false", nativeQuery = true)
    User findByPhoneNumberAndPassword(String phoneNumber, String password);
    @Query(value = "SELECT * FROM user WHERE user.id = :id AND user.deleted = false", nativeQuery = true)
    User findById(int id);



}
