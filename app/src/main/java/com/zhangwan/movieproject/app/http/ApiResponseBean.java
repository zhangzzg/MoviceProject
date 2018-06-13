package com.zhangwan.movieproject.app.http;

import com.google.gson.annotations.SerializedName;

public class ApiResponseBean<T> {


    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private T result;
    @SerializedName("message")
    private String msg;

    @SerializedName("code")
    private String code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {

    }

    public void setMsg(String info) {
        this.msg = info;
    }


}
