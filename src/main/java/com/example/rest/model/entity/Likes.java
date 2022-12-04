package com.example.rest.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
@Data
public class Likes extends BaseModel {
    @Column(name = "post_id")
    private String postId;

    @Column(name = "user_id")
    private int userId;
}
