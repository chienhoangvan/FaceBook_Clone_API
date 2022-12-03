package com.example.rest.model.response;

public class LikeResponse {
    private String like;

    public LikeResponse(String like) {
        this.like = like;
    }

    public LikeResponse() {
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
