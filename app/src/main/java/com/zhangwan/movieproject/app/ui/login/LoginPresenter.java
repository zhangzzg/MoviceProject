package com.zhangwan.movieproject.app.ui.login;

import com.zhangwan.movieproject.app.http.ApiCallBack;

import java.util.Map;

/**
 * Created by zzg on 2018/4/11.
 */

public class LoginPresenter implements LoginContract.Presenter {
    LoginContract.View mView;
    LoginSource mSource;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mSource = new LoginRespository();
    }

    @Override
    public void start() {}

    @Override
    public void destroy() {
        mSource.destroy();
    }

    @Override
    public void login(String phone, String passwd) {
        mSource.login(phone,passwd, new ApiCallBack<Object>() {

            @Override
            public void onSuccess(Object data) {
                if(data == null){
                    mView.showEmpty();
                    return;
                }
                mView.showLoginDate(data);
            }

            @Override
            public void onError(String status, String message) {
                mView.showError(status,message);
            }
        });
    }

    @Override
    public void weChatLogin(String code) {
        mSource.weChatLogin(code, new ApiCallBack<Object>() {

            @Override
            public void onSuccess(Object data) {
                if(data == null){
                    mView.showEmpty();
                    return;
                }
                mView.showWeChatDate(data);
            }

            @Override
            public void onError(String status, String message) {
                mView.showError(status,message);
            }
        });
    }

    @Override
    public void qqLogin(Map<String, String> map) {
        mSource.qqLogin(map, new ApiCallBack<Object>() {

            @Override
            public void onSuccess(Object data) {
                if(data == null){
                    mView.showEmpty();
                    return;
                }
                mView.showQQDate(data);
            }

            @Override
            public void onError(String status, String message) {
                mView.showError(status,message);
            }
        });
    }

    @Override
    public void tempLogin() {
        mView.showLoad();
        mSource.tempLogin(new ApiCallBack<Object>() {

            @Override
            public void onSuccess(Object data) {
                mView.showLoadFinish();
                if(data == null){
                    mView.showEmpty();
                    return;
                }
                mView.showTempLoginResulte(data);
            }

            @Override
            public void onError(String status, String message) {
                mView.showLoadFinish();
                mView.showError(status,message);
            }
        });
    }
}
