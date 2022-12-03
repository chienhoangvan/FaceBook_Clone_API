package com.example.rest.common;

import java.util.HashMap;

public class Constant {
    public static final String OK_CODE = "1000";
    public static final String POST_IS_NOT_EXISTED_CODE = "9992";
    public static final String CODE_VERIFY_IS_INCORRECT_CODE = "9993";
    public static final String NO_DATA_OR_END_OF_LIST_DATA_CODE = "9994";
    public static final String USER_IS_NOT_VALIDATED_CODE = "9995";
    public static final String USER_EXISTED_CODE = "9996";
    public static final String METHOD_IS_INVALID_CODE = "9997";
    public static final String TOKEN_IS_INVALID_CODE = "9998";
    public static final String EXCEPTION_ERROR_CODE = "9999";
    public static final String CAN_NOT_CONNECT_TO_DB_CODE = "1001";
    public static final String PARAMETER_IS_NOT_ENOUGH_CODE = "1002";
    public static final String PARAMETER_TYPE_IS_INVALID_CODE = "1003";
    public static final String PARAMETER_VALUE_IS_INVALID_CODE = "1004";
    public static final String UNKNOWN_ERROR_CODE = "1005";
    public static final String FILE_SIZE_IS_TOO_BIG_CODE = "1006";
    public static final String UPLOAD_FILE_FAILED_CODE = "1007";
    public static final String MAXIMUM_NUMBER_OF_IMAGES_CODE = "1008";
    public static final String NOT_ACCESS_CODE = "1009";
    public static final String ACTION_HAS_BEEN_DONE_PREVIOUSLY_BY_THIS_USER_CODE = "1010";
    public static final String COULD_NOT_PUBLISH_THIS_POST_CODE = "1011";
    public static final String LIMITED_ACCESS_CODE = "1012";

    public static final String OK_MESSAGE = "OK";
    public static final String POST_IS_NOT_EXISTED_MESSAGE = "Post is not existed";
    public static final String CODE_VERIFY_IS_INCORRECT_MESSAGE = "Code verify is incorrect";
    public static final String NO_DATA_OR_END_OF_LIST_DATA_MESSAGE = "No data or end of list data";
    public static final String USER_IS_NOT_VALIDATED_MESSAGE = "User is not validated";
    public static final String USER_EXISTED_MESSAGE = "User existed";
    public static final String METHOD_IS_INVALID_MESSAGE = "Method is invalid";
    public static final String TOKEN_IS_INVALID_MESSAGE = "Token is invalid";
    public static final String EXCEPTION_ERROR_MESSAGE = "Exception error";
    public static final String CAN_NOT_CONNECT_TO_DB_MESSAGE = "Can not connect to DB";
    public static final String PARAMETER_IS_NOT_ENOUGH_MESSAGE = "Parameter is not enough";
    public static final String PARAMETER_TYPE_IS_INVALID_MESSAGE = "Parameter type is invalid";
    public static final String PARAMETER_VALUE_IS_INVALID_MESSAGE = "Parameter value is invalid";
    public static final String UNKNOWN_ERROR_MESSAGE = "Unknown error";
    public static final String FILE_SIZE_IS_TOO_BIG_MESSAGE = "File size is too big";
    public static final String UPLOAD_FILE_FAILED_MESSAGE = "Upload file failed";
    public static final String MAXIMUM_NUMBER_OF_IMAGES_MESSAGE = "Maximum number of images";
    public static final String NOT_ACCESS_MESSAGE = "Not access";
    public static final String ACTION_HAS_BEEN_DONE_PREVIOUSLY_BY_THIS_USER_MESSAGE = "Action has been done previously by this user";
    public static final String COULD_NOT_PUBLISH_THIS_POST_MESSAGE = "Could not publish this post";
    public static final String LIMITED_ACCESS_MESSAGE = "Limited access";
    public static final boolean IS_DELETED = true;
    public static final boolean IS_NOT_DELETED = false;


    /*TODO ROOT DIRECTORY*/
    public static final String ROOT_DIRECTORY = "fb-api";
    /*TODO IMAGE FILE TYPE*/
    public static final String PNG = "png";
    public static final String JPG = "jpg";
    public static final String JPEG = "jpeg";
    /*TODO VIDEO FILE TYPE*/
    public static final String MP4 = "mp4";
    public static final String FLV = "flv";
    /*TODO BYTES CONVERT TO MB*/
    public static final double CONVERSION_TO_MB = 0.00000095367432;
//    /*TODO MILLISECONDS OF A MINUTE*/
//    public static final long millisecondsOfAMinute = 60000;
//    /*TODO MILLISECONDS OF A HOUR*/
//    public static final long millisecondsOfAHour = 3600000;
//    /*TODO MILLISECONDS OF A DAY*/
//    public static final long millisecondsOfADay = 86400000;
//    /*TODO MILLISECONDS OF A MONTH*/
//    public static final long millisecondsOfAMonth = 2592000000L;
//    /*TODO MILLISECONDS OF A YEAR*/
//    public static final long millisecondsOfAYear = 31104000000L;





    public static String getErrMsg(String errorCode) {
        return IMessage.VALUE.get(errorCode);
    }

    public interface IMessage {
        HashMap<String, String> VALUE = new HashMap<String, String>() {
            {
                put(OK_CODE, OK_MESSAGE);
                put(POST_IS_NOT_EXISTED_CODE, POST_IS_NOT_EXISTED_MESSAGE);
                put(CODE_VERIFY_IS_INCORRECT_CODE, CODE_VERIFY_IS_INCORRECT_MESSAGE);
                put(NO_DATA_OR_END_OF_LIST_DATA_CODE, NO_DATA_OR_END_OF_LIST_DATA_MESSAGE);
                put(USER_IS_NOT_VALIDATED_CODE, USER_IS_NOT_VALIDATED_MESSAGE);
                put(USER_EXISTED_CODE, USER_EXISTED_MESSAGE);
                put(METHOD_IS_INVALID_CODE, METHOD_IS_INVALID_MESSAGE);
                put(TOKEN_IS_INVALID_CODE, TOKEN_IS_INVALID_MESSAGE);
                put(EXCEPTION_ERROR_CODE, EXCEPTION_ERROR_MESSAGE);
                put(CAN_NOT_CONNECT_TO_DB_CODE, CAN_NOT_CONNECT_TO_DB_MESSAGE);
                put(PARAMETER_IS_NOT_ENOUGH_CODE, PARAMETER_IS_NOT_ENOUGH_MESSAGE);
                put(PARAMETER_TYPE_IS_INVALID_CODE, PARAMETER_TYPE_IS_INVALID_MESSAGE);
                put(PARAMETER_VALUE_IS_INVALID_CODE, PARAMETER_VALUE_IS_INVALID_MESSAGE);
                put(UNKNOWN_ERROR_CODE, UNKNOWN_ERROR_MESSAGE);
                put(FILE_SIZE_IS_TOO_BIG_CODE, FILE_SIZE_IS_TOO_BIG_MESSAGE);
                put(UPLOAD_FILE_FAILED_CODE, UPLOAD_FILE_FAILED_MESSAGE);
                put(MAXIMUM_NUMBER_OF_IMAGES_CODE, MAXIMUM_NUMBER_OF_IMAGES_MESSAGE);
                put(NOT_ACCESS_CODE, NOT_ACCESS_MESSAGE);
                put(ACTION_HAS_BEEN_DONE_PREVIOUSLY_BY_THIS_USER_CODE, ACTION_HAS_BEEN_DONE_PREVIOUSLY_BY_THIS_USER_MESSAGE);
                put(COULD_NOT_PUBLISH_THIS_POST_CODE, COULD_NOT_PUBLISH_THIS_POST_MESSAGE);
                put(LIMITED_ACCESS_CODE, LIMITED_ACCESS_MESSAGE);
            }

        };
    }



}
