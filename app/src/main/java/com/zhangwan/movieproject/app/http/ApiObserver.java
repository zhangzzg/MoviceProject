package com.zhangwan.movieproject.app.http;

import java.net.SocketTimeoutException;

import rx.Observer;

/**
 * Created by sjr on 17/1/18.
 */

public class ApiObserver<T> implements Observer<T> {

    public static final String ALL_ERROR = "400";
    public static final String SERVER_ERROR = "500";
    public static final String SERVER_TIME_OUT = "303";
    public static final String SERVER_SUCCESS = "1";

    private ApiCallBack callBack;

    public ApiObserver(ApiCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onCompleted() {
        callBack.onCompleted();
    }

    @Override
    public void onNext(T t) {
        if (t instanceof ApiResponseBean) {
            ApiResponseBean apiResponseBean = (ApiResponseBean) t;

            if (!SERVER_SUCCESS.equals(apiResponseBean.getStatus())) {
                callBack.onError(apiResponseBean.getStatus(), apiResponseBean.getMsg());
                return;
            }
            callBack.onSuccess(apiResponseBean.getResult());
            return;
        }

        callBack.onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //有网情况服务器崩溃
//        if (NetworkUtil.isConnected(MyApplication.getInstance())) {
//            callBack.onError(SERVER_ERROR, "服务器繁忙");
//
//            //无网络错误
//       } else
        if (e instanceof SocketTimeoutException) {
            callBack.onError(SERVER_TIME_OUT, "链接超时，请重新链接");

        } else {
            callBack.onError(ALL_ERROR, "服务器繁忙");
        }

    }


}
