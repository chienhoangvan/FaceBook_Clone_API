package com.example.rest.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "post")
@Data
public class Post extends BaseModel {
    @Column(name = "content")
    private String content;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "state")
    private String state;

    @Column(name = "can_comment")
    private Boolean canComment;
}
