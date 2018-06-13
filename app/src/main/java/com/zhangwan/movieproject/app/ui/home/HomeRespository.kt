package com.zhangwan.movieproject.app.ui.home

import com.gxtc.commlibrary.data.BaseRepository
import com.zhangwan.movieproject.app.bean.HomeBean
import com.zhangwan.movieproject.app.http.ApiCallBack
import com.zhangwan.movieproject.app.http.ApiObserver
import com.zhangwan.movieproject.app.http.service.HomeApiService

import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by zzg on 2018/4/11.
 */

class HomeRespository : BaseRepository(), HomeSource {
    override fun getHomeData(token: String, callBack: ApiCallBack<HomeBean>) {
        val sub = HomeApiService
                .getInstance().getHomeData(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ApiObserver(callBack))
        addSub(sub)
    }
}
