package com.example.rest.service.impl;

import com.example.rest.common.CommonException;
import com.example.rest.common.CommonResponse;
import com.example.rest.common.CommonService;
import com.example.rest.common.Constant;
import com.example.rest.model.entity.Likes;
import com.example.rest.model.response.LikeResponse;
import com.example.rest.repository.LikesRepository;
import com.example.rest.repository.PostRepository;
import com.example.rest.service.ILikeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class LikeService implements ILikeService {
    @Autowired
    private CommonService commonService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikesRepository likesRepository;

    @Override
    public CommonResponse<LikeResponse> saveLike(String token, String id) throws CommonException {
        //validate token and findUserById
        int userId = Integer.parseInt(commonService.getUserIdFromToken(token));
        //findPostById
        if (StringUtils.isEmpty(id) || postRepository.findById(Integer.parseInt(id)) == null) {
            throw new CommonException(Constant.PARAMETER_IS_NOT_ENOUGH_CODE);
        }
        if (userId < 0) {
            throw new CommonException(Constant.PARAMETER_IS_NOT_ENOUGH_CODE);
        }
        //like lần thứ 2 -> xóa like hiện tại
        Likes likeInDB = likesRepository.findByUserIdAndPostId(userId, Integer.parseInt(id));
        if (likeInDB != null) {
            try {
                //update
                if (likeInDB.isDeleted() == Constant.IS_DELETED) {
                    likeInDB.setDeleted(Constant.IS_NOT_DELETED);
                } else {
                    likeInDB.setDeleted(Constant.IS_DELETED);
                }
                likeInDB.setUserId(userId);
                likeInDB.setPostId(id);
                likesRepository.save(likeInDB);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new CommonException(Constant.EXCEPTION_ERROR_CODE);
            }
        } else {
            //like lần thứ nhất
            //insert
            try {
                Likes like = new Likes();
                like.setDeleted(Constant.IS_NOT_DELETED);
                like.setUserId(userId);
                like.setPostId(id);
                likesRepository.save(like);
            } catch (Exception e) {
                throw new CommonException(Constant.EXCEPTION_ERROR_CODE);
            }
        }
        //find number of like in the post
        List<LikeResponse> numberPostLikes = new ArrayList<>();
        LikeResponse numberLike = this.getNumberLikes(id);
        numberPostLikes.add(numberLike);
        return new CommonResponse(Constant.OK_CODE, Constant.OK_MESSAGE, numberPostLikes);
    }

    @Override
    public LikeResponse getNumberLikes(String postId) {
        List<Likes> likes = likesRepository.findByPostId(postId);
        LikeResponse likeResponse = new LikeResponse();
        likeResponse.setLike(String.valueOf(likes.size()));
        return likeResponse;
    }
}
