package com.example.rest.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "blocks")
@Data
public class Blocks extends BaseModel {
    @Column(name = "id_blocks")
    private int idBlocks;
    @Column(name = "id_blocked")
    private int idBlocked;

    public int getIdBlocks() {
        return idBlocks;
    }

    public void setIdBlocks(int idBlocks) {
        this.idBlocks = idBlocks;
    }

    public int getIdBlocked() {
        return idBlocked;
    }

    public void setIdBlocked(int idBlocked) {
        this.idBlocked = idBlocked;
    }
}
