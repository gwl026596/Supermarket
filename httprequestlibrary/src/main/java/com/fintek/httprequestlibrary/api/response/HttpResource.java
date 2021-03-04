package com.fintek.httprequestlibrary.api.response;

/**
 * 多个不同字段的返回响应重新定义该类
 * @param <T>
 */
public class HttpResource<T> {
    private String code;
    private T data;
    private String message;



    public HttpResource() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HttpResource{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
