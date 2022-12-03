package com.example.rest.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<CommonException> handleControllerException(CommonException commonException,WebRequest request) throws Exception {

        return new ResponseEntity(commonException,HttpStatus.BAD_REQUEST);
    }

}