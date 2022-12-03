package com.example.rest.model.response.post;

import com.example.rest.model.response.BaseResponse;

public class Image extends BaseResponse {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
