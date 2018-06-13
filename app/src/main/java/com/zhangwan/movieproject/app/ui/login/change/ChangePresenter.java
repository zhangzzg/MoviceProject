package com.zhangwan.movieproject.app.ui.login.change;

import com.zhangwan.movieproject.app.http.ApiCallBack;
import com.zhangwan.movieproject.app.ui.login.LoginContract;
import com.zhangwan.movieproject.app.ui.login.LoginRespository;
import com.zhangwan.movieproject.app.ui.login.LoginSource;

import java.util.Map;

/**
 * Created by zzg on 2018/4/11.
 */

public class ChangePresenter implements ChangeContract.Presenter {
    ChangeContract.View mView;
    LoginSource mSource;

    public ChangePresenter(ChangeContract.View view) {
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
    public void resetPassWord(String phone, String passwd, String codetoken, String yzm) {
        mSource.resetPassWord(phone,passwd,codetoken,yzm, new ApiCallBack<Object>() {

            @Override
            public void onSuccess(Object data) {
                if(data == null){
                    mView.showEmpty();
                    return;
                }
                mView.showResult(data);
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
}
