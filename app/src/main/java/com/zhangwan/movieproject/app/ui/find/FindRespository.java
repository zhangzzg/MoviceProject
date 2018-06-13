package com.zhangwan.movieproject.app.ui.find;

import com.gxtc.commlibrary.data.BaseRepository;
import com.gxtc.commlibrary.utils.GsonUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.zhangwan.movieproject.app.bean.ChannelBean;
import com.zhangwan.movieproject.app.bean.FindBean;
import com.zhangwan.movieproject.app.bean.HomeBean;
import com.zhangwan.movieproject.app.http.ApiCallBack;
import com.zhangwan.movieproject.app.http.ApiObserver;
import com.zhangwan.movieproject.app.http.ApiResponseBean;
import com.zhangwan.movieproject.app.http.service.HomeApiService;
import com.zhangwan.movieproject.app.ui.home.HomeSource;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zzg on 2018/4/11.
 */

public class FindRespository extends BaseRepository implements FindSource {

    @Override
    public void getData(String token, String cid, String word, String rem, int page, ApiCallBack<FindBean> callBack) {
        Subscription sub = HomeApiService
                .getInstance().getData(token, cid, word, rem, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<FindBean>>(callBack));
        addSub(sub);
    }


    @Override
    public void getChannelData(String token, ApiCallBack<List<ChannelBean>> callBack) {
        Subscription sub = HomeApiService
                .getInstance().getChannelData(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<ChannelBean>>>(callBack));
        addSub(sub);
    }
}
