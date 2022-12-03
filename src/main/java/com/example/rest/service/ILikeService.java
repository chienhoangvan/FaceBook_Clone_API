package com.example.rest.service;

import com.example.rest.common.CommonException;
import com.example.rest.common.CommonResponse;
import com.example.rest.model.entity.Likes;
import com.example.rest.model.response.CommentResponse;
import com.example.rest.model.response.LikeResponse;

public interface ILikeService {
    CommonResponse<LikeResponse> saveLike(String token, String id) throws CommonException;

    LikeResponse getNumberLikes(String postId);
}
