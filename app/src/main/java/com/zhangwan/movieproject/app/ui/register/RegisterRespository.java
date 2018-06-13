package com.zhangwan.movieproject.app.ui.register;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.movieproject.app.bean.HomeBean;
import com.zhangwan.movieproject.app.http.ApiCallBack;
import com.zhangwan.movieproject.app.http.ApiObserver;
import com.zhangwan.movieproject.app.http.ApiResponseBean;
import com.zhangwan.movieproject.app.http.service.HomeApiService;
import com.zhangwan.movieproject.app.ui.home.HomeSource;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zzg on 2018/4/11.
 */

public class RegisterRespository extends BaseRepository implements RegsterSource {

    @Override
    public void register(String phone, String passwd, String codetoken, String yzm,
                         ApiCallBack<Object> callBack) {
        Subscription sub = HomeApiService
                .getInstance().register(phone,passwd,codetoken,yzm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<Object>>(callBack));
        addSub(sub);
    }

    @Override
    public void getMobilecode(String phone, ApiCallBack<Object> callBack) {
        Subscription sub = HomeApiService
                .getInstance().getMobilecode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<Object>>(callBack));
        addSub(sub);
    }
}
