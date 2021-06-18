package com.rony.erpsoft.configuration;

import org.springframework.http.HttpStatus;

public class AppResponse<T> {

    private HttpStatus status;
    private String message;
    private T body;
    private Integer code;

    private AppResponse(HttpStatus status) {
        this.status = status;
        this.code = status.value();
    }

    private AppResponse() {
    }

    public static AppResponse build(HttpStatus status) {
        return new AppResponse(status);
    }

    public Integer getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public AppResponse message(String message) {
        this.message = message;
        return this;
    }

    public T getBody() {
        return body;
    }

    public AppResponse body(T data) {
        this.body = data;
        return this;
    }

}
