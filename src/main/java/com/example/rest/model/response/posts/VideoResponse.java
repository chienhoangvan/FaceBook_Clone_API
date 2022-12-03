package com.example.rest.model.response.posts;

public class VideoResponse {
    private String url;
    private String thumb;

    public VideoResponse(String url, String thumb) {
        super();
        this.url = url;
        this.thumb = thumb;
    }

    public VideoResponse() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
