package com.example.rest.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "file")
@Data
public class File extends BaseModel {
    @Column(name = "content")
    private String content;

    @Column(name = "post_id")
    private int postId;
}

