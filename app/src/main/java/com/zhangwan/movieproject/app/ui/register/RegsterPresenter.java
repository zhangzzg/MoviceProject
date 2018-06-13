package com.zhangwan.movieproject.app.ui.register;

import com.zhangwan.movieproject.app.bean.HomeBean;
import com.zhangwan.movieproject.app.http.ApiCallBack;
import com.zhangwan.movieproject.app.ui.home.HomeContract;
import com.zhangwan.movieproject.app.ui.home.HomeRespository;
import com.zhangwan.movieproject.app.ui.home.HomeSource;
import com.zhangwan.movieproject.app.ui.login.LoginRespository;
import com.zhangwan.movieproject.app.ui.login.LoginSource;

/**
 * Created by zzg on 2018/4/11.
 */

public class RegsterPresenter implements RegisterContract.Presenter {
    RegisterContract.View mView;
    RegsterSource         mSource;
    LoginSource           mdatas;

    public RegsterPresenter(RegisterContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mSource = new RegisterRespository();
        mdatas = new LoginRespository();
    }

    @Override
    public void start() {}

    @Override
    public void destroy() {
        mSource.destroy();
        mdatas.destroy();
    }

    @Override
    public void register(String phone, String passwd, String codetoken, String yzm) {
        mSource.register(phone, passwd, codetoken, yzm, new ApiCallBack<Object>() {

            @Override
            public void onSuccess(Object data) {
                if(data == null){
                    mView.showEmpty();
                    return;
                }
                mView.showRegisterDate(data);
            }

            @Override
            public void onError(String status, String message) {
             mView.showError(status,message);
            }
        });
    }

    @Override
    public void getMobilecode(String phone) {
        mSource.getMobilecode(phone, new ApiCallBack<Object>() {

            @Override
            public void onSuccess(Object data) {
                if(data == null){
                    mView.showEmpty();
                    return;
                }
                mView.showMobilecode(data);
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
        mdatas.tempLogin(new ApiCallBack<Object>() {

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
