package com.giaynhap.securechat.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    @JsonProperty("transction_id")
    private String transctionId;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public static ApiResponse getErrorResponse(int code, String Message){
        return new ApiResponse<Object>(code,Message,null);
    }

    public ApiResponse(int code, String message, T data,String transctionId) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.transctionId = transctionId;
    }

    public String getTransctionId() {
        return transctionId;
    }

    public void setTransctionId(String transctionId) {
        this.transctionId = transctionId;
    }
}
