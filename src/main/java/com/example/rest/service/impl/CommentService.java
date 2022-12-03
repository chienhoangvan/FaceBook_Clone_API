package com.example.rest.service.impl;

import com.example.rest.common.CommonException;
import com.example.rest.common.CommonResponse;
import com.example.rest.common.CommonService;
import com.example.rest.common.Constant;
import com.example.rest.model.entity.Comment;
import com.example.rest.model.entity.Post;
import com.example.rest.model.entity.User;
import com.example.rest.model.response.CommentResponse;
import com.example.rest.model.response.Poster;
import com.example.rest.repository.CommentRepository;
import com.example.rest.repository.PostRepository;
import com.example.rest.repository.UserRepository;
import com.example.rest.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommonService commonService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public CommonResponse<CommentResponse> setComment(String token ,String id, String comment, String index, String count) throws CommonException {
        commonService.checkCommonValidate(token,id, comment, index, count);


        Post post = postRepository.findById(Integer.parseInt(id));


// truyen vao id bai viet ko ton tai
    if (post == null){
        throw new CommonException(Constant.POST_IS_NOT_EXISTED_CODE);
    }

        int userId = Integer.parseInt(commonService.getUserIdFromToken(token));


        //Chặn test case trả ra mã lỗi

//    truyen vao nhung khong ket noi duoc database
        try {
            Comment commentDb = commentRepository.save(setCommonCommentInfo(userId, comment, Integer.parseInt(id)));


        }catch (Exception e){
            throw new CommonException(Constant.CAN_NOT_CONNECT_TO_DB_CODE);
        }

    List<Comment> comments = commentRepository.findByPostId(Integer.parseInt(id));
    int sizeOfComments = comments.size();
    if(sizeOfComments == 0){
        throw new CommonException(Constant.NO_DATA_OR_END_OF_LIST_DATA_CODE);
    }
//    truyen vao sai index va count
    int indexInteger = Integer.parseInt(index);
    int countInteger = Integer.parseInt(count);
    if (indexInteger < 0 || indexInteger > sizeOfComments - 1 || countInteger <= 0
            || indexInteger + countInteger > sizeOfComments){
        throw new CommonException(Constant.PARAMETER_VALUE_IS_INVALID_CODE);
    }






        //Trả ra dữ liệu trường hợp thành công


        List<CommentResponse> list = new ArrayList<>();
        //index = 1, count = 3 -> 1 2 3
        for(int i = indexInteger;i < indexInteger + countInteger;i++){
            Comment commentEntity = comments.get(i);
            int userId1 = commentEntity.getUserId();
            User user = userRepository.findById(userId1); //Thông tin người comment

            //Set comment response
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setComment(commentEntity.getContent());
            commentResponse.setId(String.valueOf(commentEntity.getId()));
            commentResponse.setCreated(String.valueOf(commentEntity.getCreatedDate()));

            List<Poster> posters = new ArrayList<>();
            Poster poster = new Poster();
            poster.setName(user.getName());
            poster.setAvatar(user.getLinkAvatar());
            poster.setId(String.valueOf(user.getId()));
            posters.add(poster);

            commentResponse.setPoster(posters);

            list.add(commentResponse);
        }

    return new CommonResponse<CommentResponse>(Constant.OK_CODE,Constant.OK_MESSAGE,list);

    }

    private Comment setCommonCommentInfo(int userId, String comment, int postId){


        Comment commentEntity = new Comment();

        commentEntity.setDeleted(false);
        commentEntity.setCreatedDate(System.currentTimeMillis());
        commentEntity.setCreatedBy(String.valueOf(userId));
        commentEntity.setContent(comment);
        commentEntity.setUserId(userId);
        commentEntity.setPostId(postId);

        return commentEntity;
    }
}
