package com.example.rest.model.response.posts;

public class AuthorResponse {
    private String id;
    private String username;
    private String avatar;
    private String online;

    public AuthorResponse(String id, String username, String avatar, String online) {
        super();
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.online = online;
    }

    public AuthorResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
