package com.example.rest.model.mapper;


import com.example.rest.model.entity.Post;
import com.example.rest.model.response.posts.PostsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class PostMapper {
    @Autowired
    ObjectMapper objectMapper;

    public PostsResponse toResponse(Post post) {
        //convert to format Date
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createdPost = newFormat.format(post.getCreatedDate());

        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setId(String.valueOf(post.getId()));
        postsResponse.setComment(String.valueOf(10));
        postsResponse.setContent(post.getContent());
        //converto to image or video
        postsResponse.setUser_id(String.valueOf(post.getUserId()));
        postsResponse.setCreated(createdPost);
        return postsResponse;
    }

    public Post toPost(PostsResponse postsResponse) {
        return objectMapper.convertValue(postsResponse, Post.class);
    }

}