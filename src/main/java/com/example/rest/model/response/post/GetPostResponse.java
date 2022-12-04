package com.example.rest.model.response.post;

import com.example.rest.model.response.BaseResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetPostResponse extends BaseResponse {
    private String postId;

    private String described;

    private Date created;

    private Date modified;

    private Long like;

    private Long comment;

    private Boolean isLiked;

    private List<Image> image;

    private List<Video> video;

    private Author author;

    private String state;

    private Boolean isBlocked;

    private Boolean canEdit;

    private Boolean banned;

    private Boolean canComment;

    private String url;

    private List<Message> messages;

}
