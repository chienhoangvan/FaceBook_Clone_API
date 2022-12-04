package com.example.rest.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "report")
@Data
public class Report extends BaseModel{
    @Column(name = "user_id")
    private int userId;
    @Column(name = "post_id")
    private int postId;
}