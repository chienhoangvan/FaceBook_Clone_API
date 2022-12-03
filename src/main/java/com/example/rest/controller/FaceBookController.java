package com.example.rest.controller;

import com.example.rest.common.CommonException;
import com.example.rest.common.CommonResponse;
import com.example.rest.model.response.CommentResponse;
import com.example.rest.model.response.LikeResponse;
import com.example.rest.model.response.LoginResponse;
import com.example.rest.model.response.SignUpResponse;
import com.example.rest.model.response.post.AddPostResponse;
import com.example.rest.model.response.posts.DataResponse;
import com.example.rest.service.*;
import com.example.rest.service.impl.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/")
public class FaceBookController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IPostService postService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    ItemService itemService;
    @Autowired
    private ILikeService iLikeService;
    @Autowired
    private IReportService reportService;

    @PostMapping(path = "/signup")
    public ResponseEntity<CommonResponse<SignUpResponse>> signUp(
            @RequestParam(name = "phoneNumber") String phoneNumber,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "uuid") String uuid) throws CommonException {
        return new ResponseEntity<>(userService.signUp(phoneNumber, password, uuid), HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(
            @RequestParam(name = "phoneNumber") String phoneNumber,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "deviceId") String deviceId) throws CommonException {
        return new ResponseEntity<>(userService.login(phoneNumber, password, deviceId), HttpStatus.OK);
    }

    @PostMapping(path = "/log-out")
    public ResponseEntity<CommonResponse> logout(
            @RequestParam(name = "token") String token) throws CommonException {
        return new ResponseEntity<>(userService.logout(token), HttpStatus.OK);
    }

    @PostMapping(path = "/add-post")
    public ResponseEntity<CommonResponse<AddPostResponse>> addPost(
            @RequestParam(name = "token") String token,
            @RequestParam(name = "image") MultipartFile[] image,
            @RequestParam(name = "video") MultipartFile video,
            @RequestParam(name = "described") String described,
            @RequestParam(name = "status") String status) throws Exception {
        return new ResponseEntity<>(postService.addPost(token, image, video, described, status), HttpStatus.OK);
    }

    @PostMapping(path = "/delete-post")
    public ResponseEntity<CommonResponse> deletePost(
            @RequestParam(name = "token") String token, @RequestParam(name = "id") String postId) throws IOException, CommonException {
        return new ResponseEntity<>(postService.deletePost(token, postId), HttpStatus.OK);
    }

    @PostMapping(path = "/edit-post")
    public ResponseEntity<CommonResponse> editPost(
            @RequestParam(name = "token") String token,
            @RequestParam(name = "id") String postId,
            @RequestParam(name = "described") String described,
            @RequestParam(name = "status") String status,
            @RequestParam(name = "image") MultipartFile[] newImageFiles,
            @RequestParam(name = "image_del") List<String> imageIdsDeleted,
            @RequestParam(name = "image_sort") List<String> imageIdsSort,
            @RequestParam(name = "video") MultipartFile video,
            @RequestParam(name = "thumb") MultipartFile thumb,
            @RequestParam(name = "auto_accept") String autoAccept,
            @RequestParam(name = "auto_block") String autoBlock) throws Exception {
        return new ResponseEntity<>(postService.editPost(token, postId, described, status, newImageFiles, imageIdsDeleted, imageIdsSort, video, thumb, autoAccept, autoBlock), HttpStatus.OK);
    }

    @PostMapping(path = "/set-comment")
    public ResponseEntity<CommonResponse<CommentResponse>> like(
            @RequestParam(name = "token") String token,
            @RequestParam(name = "id") String postId,
            @RequestParam(name = "comment") String comment,
            @RequestParam(name = "index") String index,
            @RequestParam(name = "count") String count) throws CommonException {
        return new ResponseEntity<>(commentService.setComment(token, postId, comment, index, count), HttpStatus.OK);
    }

    @PostMapping(path = "/report-post")
    public ResponseEntity<CommonResponse> reportPost(
            @RequestParam(name = "token") String token,
            @RequestParam(name = "id") String postId,
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "details") String details) throws CommonException {
        return new ResponseEntity<>(reportService.reportPost(postId, token, subject, details), HttpStatus.OK);
    }

    @PostMapping(path = "/like")
    public ResponseEntity<CommonResponse<LikeResponse>> like(
            @RequestParam(name = "token") String token, @RequestParam(name = "id") String postId) throws CommonException {
        return new ResponseEntity<>(iLikeService.saveLike(token, postId), HttpStatus.OK);
    }

    @PostMapping(path = "/get-list-posts")
    public ResponseEntity<CommonResponse<DataResponse>> getListPosts(
            @RequestParam(required = false, name = "token") String token,
            @RequestParam(required = false, name = "user_id") String user_id,
            @RequestParam(required = false, name = "campaign_id") String in_campaign,
            @RequestParam(required = false, name = "campaign_id") String campaign_id,
            @RequestParam(required = false, name = "latitude") String latitude,
            @RequestParam(required = false, name = "longitude") String longitude,
            @RequestParam(required = false, name = "last_id") String last_id,
            @RequestParam(defaultValue = "0", required = false, name = "index") String index,
            @RequestParam(defaultValue = "20", required = false, name = "count") String count) throws CommonException {
        return new ResponseEntity<>(postService.getListPosts(token, user_id, in_campaign, campaign_id, latitude, longitude, last_id, index, count), HttpStatus.OK);
    }

    @PostMapping(path = "/check-new-item")
    public ResponseEntity<CommonResponse<String>> checkNewItem(
            @RequestParam(name = "last_id") String lastId,
            @RequestParam(name = "category_id") String categoryId) throws CommonException {
        return new ResponseEntity<>(itemService.checkNewItem(lastId, categoryId), HttpStatus.OK);
    }
}
