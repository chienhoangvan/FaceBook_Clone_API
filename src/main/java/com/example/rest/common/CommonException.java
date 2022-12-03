package com.example.rest.common;

import org.springframework.stereotype.Component;

@Component
public class CommonException extends Exception{
    private String code;
    public CommonException(){
        super();
    }
    public CommonException(String code){
        super(Constant.getErrMsg(code));
        this.code = code;
    }

    public String getCode() {

        return code;
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
