package com.zhangwan.movieproject.app.http;

import java.io.IOException;

/**
 * Created by laoshiren on 2018/3/21.
 * 解决服务器同一字段返回不同类型数据问题
 */

public class DataResultException extends IOException {
    private String msg;
    private String code;

    public DataResultException(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public DataResultException(String message, String msg, String code) {
        super(message);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
