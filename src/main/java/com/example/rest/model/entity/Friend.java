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

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public int getIdB() {
        return idB;
    }

    public void setIdB(int idB) {
        this.idB = idB;
    }
}
