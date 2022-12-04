package com.example.rest.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "friend")
@Data
public class Friend extends BaseModel {
    @Column(name = "idA")
    private int idA;
    @Column(name = "idB")
    private int idB;
}
