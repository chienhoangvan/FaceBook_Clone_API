package com.example.rest.model.response.posts;

import java.util.List;

public class DataResponse {
    private List<PostsResponse> posts;
    private String new_items;
    private String last_id;

    public DataResponse(List<PostsResponse> posts, String new_items, String last_id) {
        super();
        this.posts = posts;
        this.new_items = new_items;
        this.last_id = last_id;
    }


    public DataResponse(){}

    public List<PostsResponse> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsResponse> posts) {
        this.posts = posts;
    }

    public String getNew_items() {
        return new_items;
    }

    public void setNew_items(String new_items) {
        this.new_items = new_items;
    }

    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }
}
