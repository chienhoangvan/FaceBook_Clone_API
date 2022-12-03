package com.example.rest.model.response.post;

import com.example.rest.model.response.BaseResponse;

public class Author extends BaseResponse {
    private String name;
    private String avatar;
    private String online;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
