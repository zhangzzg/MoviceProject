package com.zhangwan.movieproject.app.http;


public abstract class ApiCallBack<T> {

    public void onCompleted(){}

    public abstract void onSuccess(T data);

    public abstract void onError(String status,String message);
}
