package com.example.rest.common;

import com.example.rest.model.entity.User;
import com.example.rest.model.response.BaseResponse;
import com.example.rest.repository.UserRepository;
import com.example.rest.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class CommonService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
//    private final MappingErrorCode mappingErrorCode;

    //Check != null và != "" cho các trường bắt buộc phải truyền
    //Object... thể hiện là mảng đối tượng truyền vào, tuy nhiên không bị giới hạn số lượng phần tử như static array
    //tức là truyền bao nhiêu tham số cũng được
    public CommonResponse checkCommonValidate(Object... objects) throws CommonException { // 1 chức năng -> 4 trường bắt buộc phải truyền
        // phải check -> static array [10]
        for (int i = 0; i < objects.length; i++) {
            if (StringUtils.isEmpty(objects[i].toString())) {
                throw new CommonException(Constant.PARAMETER_IS_NOT_ENOUGH_CODE);
            }
        }
        return new CommonResponse(Constant.OK_CODE, Constant.OK_MESSAGE, null);
    }

    //Check validate Phone number
    public CommonResponse checkPhoneNumberValid(String phoneNumber) throws CommonException {
        Pattern pattern = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new CommonException(Constant.PARAMETER_VALUE_IS_INVALID_CODE);
        }
        return new CommonResponse(Constant.OK_CODE, Constant.OK_MESSAGE, null);
    }

    //Check validate password
    public CommonResponse checkPasswordValid(String password) throws CommonException {

        int length = password.length();
        if (length < 6 || length > 10) {
            throw new CommonException(Constant.PARAMETER_VALUE_IS_INVALID_CODE);
        }
        return new CommonResponse(Constant.OK_CODE, Constant.OK_MESSAGE, null);
    }

    //Get user id from token
    public String getUserIdFromToken(String token) throws CommonException {
        String userId = "-1";
        if (jwtProvider.validateToken(token)) {
            String phoneNumber = jwtProvider.getPhoneNumberFromJWT(token);

            User user = userRepository.findByPhoneNumber(phoneNumber);
            if (user == null) {
                throw new CommonException(Constant.TOKEN_IS_INVALID_CODE);
            }
            userId = String.valueOf(user.getId());
        }else{
            throw new CommonException(Constant.TOKEN_IS_INVALID_CODE);
        }
        return userId;
    }
    //đếm số từ trong 1 chuỗi
    public int countWordInString(String content){
        content = content.trim();
        char[] chars = content.toCharArray();
        int count = 0;
        if(chars.length > 0){
            for(int i =0;i < chars.length;i++){
                if(chars[i] == ' '){
                    count ++;
                }
            }
            count ++;
        }
        return count;
    }


}
