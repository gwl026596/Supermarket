package com.fintek.httprequestlibrary.api.error;


/**
 * 错误回调类
 */
public class HttpError {
    private String message;
    private String errorCode;

    public HttpError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
