package com.example.rest.model.mapper;

import com.example.rest.model.entity.Post;
import com.example.rest.model.entity.User;
import com.example.rest.model.response.posts.AuthorResponse;
import com.example.rest.model.response.posts.PostsResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public AuthorResponse toResponse(User user) {
        AuthorResponse userResponse = new AuthorResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setUsername(user.getName());
        userResponse.setAvatar(user.getLinkAvatar());
        return userResponse;
    }
}
