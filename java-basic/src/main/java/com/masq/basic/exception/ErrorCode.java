package com.masq.basic.exception;

/**
 * @title ExceptionEnum
 * @Author masq
 * @Date: 2021/8/31 下午4:53
 * @Version 1.0
 */
public enum ErrorCode {
    /**
     * code 异常码
     * message 异常信息
     */
    NO_AUTHENTICATION(401, "请登录"), NO_AUTHORIZATION(403, "没有权限"), SERVER_ERROR(500, "服务器遇到一些问题");

    /**
     * 异常代码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

}
