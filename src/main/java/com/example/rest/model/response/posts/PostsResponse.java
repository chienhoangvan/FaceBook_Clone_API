package com.example.rest.model.response.posts;

import java.util.Date;
import java.util.List;

public class PostsResponse {
    private String id;
    private String name;
    //check lại kiểu trả về
    private String content;
    private List<String> image;
    //check lại kiểu trả về
    private List<VideoResponse> video;
    private String described;
    private String created;
    private String like;
    private String comment;
    private String is_like;
    private String is_block;
    private String can_comment;
    private String banned;
    private String state;
    private AuthorResponse author;
    private String in_campaign;
    private String campaign_id;
    private String user_id;

    public PostsResponse(String id, String name, String content, List<String> image, List<VideoResponse> video, String described, String created, String like, String comment, String is_like, String is_block, String can_comment, String banned, String state, AuthorResponse author, String in_campaign, String campaign_id, String user_id) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.image = image;
        this.video = video;
        this.described = described;
        this.created = created;
        this.like = like;
        this.comment = comment;
        this.is_like = is_like;
        this.is_block = is_block;
        this.can_comment = can_comment;
        this.banned = banned;
        this.state = state;
        this.author = author;
        this.in_campaign = in_campaign;
        this.campaign_id = campaign_id;
        this.user_id = user_id;
    }

    public PostsResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public List<VideoResponse> getVideo() {
        return video;
    }

    public void setVideo(List<VideoResponse> video) {
        this.video = video;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public String getIs_block() {
        return is_block;
    }

    public void setIs_block(String is_block) {
        this.is_block = is_block;
    }

    public String getCan_comment() {
        return can_comment;
    }

    public void setCan_comment(String can_comment) {
        this.can_comment = can_comment;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public AuthorResponse getAuthor() {
        return author;
    }

    public void setAuthor(AuthorResponse author) {
        this.author = author;
    }

    public String getIn_campaign() {
        return in_campaign;
    }

    public void setIn_campaign(String in_campaign) {
        this.in_campaign = in_campaign;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
