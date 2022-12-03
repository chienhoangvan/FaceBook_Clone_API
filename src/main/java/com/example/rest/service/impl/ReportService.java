package com.example.rest.service.impl;

import com.example.rest.common.CommonException;
import com.example.rest.common.CommonResponse;
import com.example.rest.common.CommonService;
import com.example.rest.common.Constant;
import com.example.rest.model.entity.Comment;
import com.example.rest.model.entity.Post;
import com.example.rest.model.entity.Report;
import com.example.rest.model.response.CommentResponse;
import com.example.rest.repository.PostRepository;
import com.example.rest.repository.ReportRepository;
import com.example.rest.security.JwtProvider;
import com.example.rest.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService implements IReportService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ReportRepository reportRepository;



    @Override
    public CommonResponse reportPost(String id, String token, String subject, String details) throws CommonException {
        commonService.checkCommonValidate(id, token, subject, details);


        Post post = postRepository.findById(Integer.parseInt(id));
        if (post == null){
            throw new CommonException(Constant.POST_IS_NOT_EXISTED_CODE);
        }



    int userId = Integer.parseInt(commonService.getUserIdFromToken(token));

        Report report = reportRepository.findByUserIdAndPostId(userId,Integer.parseInt(id));

        if (report != null ){
            throw new CommonException(Constant.ACTION_HAS_BEEN_DONE_PREVIOUSLY_BY_THIS_USER_CODE);
        }

    try{
        Report reportDb = reportRepository.save(setCommonReportInfo(userId, Integer.parseInt(id)));
    }catch (Exception e){
        throw new CommonException(Constant.CAN_NOT_CONNECT_TO_DB_CODE);
    }

        //Truong hop tra ra du lieu thanh cong

        return new CommonResponse<CommentResponse>(Constant.OK_CODE,Constant.OK_MESSAGE,null);
    }



    private Report setCommonReportInfo(int userId,  int postId){


        Report reportEntity = new Report();

        reportEntity.setDeleted(false);
        reportEntity.setCreatedDate(System.currentTimeMillis());
        reportEntity.setCreatedBy(String.valueOf(userId));

        reportEntity.setUserId(userId);
        reportEntity.setPostId(postId);

        return reportEntity;
    }

}
