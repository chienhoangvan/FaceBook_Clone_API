package com.example.rest.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
@Data
public class Comment extends BaseModel {
    @Column(name = "content")
    private String content;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "post_id")
    private int postId;
}