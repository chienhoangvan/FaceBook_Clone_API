package com.example.rest.model.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
@Component
@Entity
@Table(name = "user")
@Data
public class User extends BaseModel {
//    name nvarchar(50) not null,
//    password nvarchar(255) not null,
//    phone_number varchar(10) not null,
//    link_avatar nvarchar(255) not null
//    token text not null

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "link_avatar")
    private String linkAvatar;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "token")
    private String token;
}
