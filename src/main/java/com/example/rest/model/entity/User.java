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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
