package com.example.rest.service;

import com.example.rest.common.CommonException;
import com.example.rest.common.CommonResponse;
import com.example.rest.model.response.CommentResponse;

public interface ICommentService {
    CommonResponse<CommentResponse> setComment(String token,String id, String comment, String index, String count) throws CommonException;
}
