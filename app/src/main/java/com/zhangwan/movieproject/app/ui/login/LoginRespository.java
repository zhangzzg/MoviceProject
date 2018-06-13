package com.zhangwan.movieproject.app.ui.login;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.movieproject.app.http.ApiCallBack;
import com.zhangwan.movieproject.app.http.ApiObserver;
import com.zhangwan.movieproject.app.http.ApiResponseBean;
import com.zhangwan.movieproject.app.http.service.HomeApiService;

import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zzg on 2018/4/11.
 */

public class LoginRespository extends BaseRepository implements LoginSource {

    @Override
    public void login(String phone, String passwd, ApiCallBack<Object> callBack) {
        Subscription sub = HomeApiService
                .getInstance().login(phone,passwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<Object>>(callBack));
        addSub(sub);
    }
    //已经不做第三方登录了
    @Override
    public void weChatLogin(String code, ApiCallBack<Object> callBack) {
        Subscription sub = HomeApiService
                .getInstance().weiChatlogin(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<Object>>(callBack));
        addSub(sub);
    }
    //已经不做第三方登录了
    @Override
    public void qqLogin(Map<String, String> map, ApiCallBack<Object> callBack) {
        Subscription sub = HomeApiService
                .getInstance().qqlogin(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<Object>>(callBack));
        addSub(sub);
    }

    @Override
    public void resetPassWord(String phone, String passwd, String codetoken, String yzm,
                              ApiCallBack<Object> callBack) {
        Subscription sub = HomeApiService
                .getInstance().resetPassWord(phone,passwd,codetoken,yzm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<Object>>(callBack));
        addSub(sub);
    }

    @Override
    public void getMobilecode(String phone, ApiCallBack<Object> callBack) {
        Subscription sub = HomeApiService
                .getInstance().resetmobilecode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<Object>>(callBack));
        addSub(sub);
    }

    @Override
    public void tempLogin(ApiCallBack<Object> callBack) {
        Subscription sub = HomeApiService
                .getInstance().tempLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<Object>>(callBack));
        addSub(sub);
    }
}
