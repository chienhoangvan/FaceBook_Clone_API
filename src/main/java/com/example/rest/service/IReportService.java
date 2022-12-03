package com.example.rest.service;

import com.example.rest.common.CommonException;
import com.example.rest.common.CommonResponse;
import com.example.rest.model.response.CommentResponse;

public interface IReportService {

    CommonResponse reportPost(String id, String token, String subject, String details) throws CommonException;
}
