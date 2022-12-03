package com.example.rest.service;

import com.example.rest.common.CommonException;
import com.example.rest.common.CommonResponse;
import com.example.rest.model.response.LoginResponse;
import com.example.rest.model.response.SignUpResponse;

public interface IUserService {
    CommonResponse<SignUpResponse> signUp(String phoneNumber,String password,String uuid) throws CommonException;
    CommonResponse<LoginResponse> login(String phoneNumber,String password,String deviceId) throws CommonException;
    CommonResponse logout(String token) throws CommonException;
}
