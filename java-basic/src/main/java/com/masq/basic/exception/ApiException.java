package com.masq.basic.exception;

/**
 * @title ApiException
 * @Author masq
 * @Date: 2021/8/31 下午4:51
 * @Version 1.0
 */
public class ApiException extends RuntimeException {
    private Integer code;

    public ApiException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

    public ApiException() {
        this(ErrorCode.SERVER_ERROR);
    }

    public ApiException(String message) {
        this(ErrorCode.SERVER_ERROR.getCode(), message);
    }

}
