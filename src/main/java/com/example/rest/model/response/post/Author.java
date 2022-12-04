package com.example.rest.model.response.post;

import com.example.rest.model.response.BaseResponse;
import lombok.Data;

@Data
public class Author extends BaseResponse {
    private String name;
    private String avatar;
    private Boolean isOnline;
}