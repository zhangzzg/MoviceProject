package com.zhangwan.movieproject.app.ui.mine

import com.gxtc.commlibrary.data.BaseRepository
import com.zhangwan.movieproject.app.bean.HomeBean
import com.zhangwan.movieproject.app.bean.TaskBean
import com.zhangwan.movieproject.app.bean.UseInfoBean
import com.zhangwan.movieproject.app.http.ApiCallBack
import com.zhangwan.movieproject.app.http.ApiObserver
import com.zhangwan.movieproject.app.http.ApiResponseBean
import com.zhangwan.movieproject.app.http.service.HomeApiService
import com.zhangwan.movieproject.app.http.service.MineApiService
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by zzg on 2018/4/14.
 */
class MineRespostory : BaseRepository(),MineSource{
    override fun getTask(token: String, callBack: ApiCallBack<TaskBean>) {
        val sub = MineApiService
                .getInstance().getTask(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ApiObserver(callBack))
        addSub(sub)
    }

    override fun updateUserInfo(token: String?, nickname: String?, face: String?, callBack: ApiCallBack<Object>) {
        val sub = MineApiService
                .getInstance().updateUserInfo(token,nickname,face)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ApiObserver(callBack))
        addSub(sub)
    }

    override fun getUserInfo(token: String, callBack: ApiCallBack<UseInfoBean>) {
        val sub = MineApiService
                .getInstance().getUserInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ApiObserver(callBack))
        addSub(sub)
    }

    override fun getWatchHistory(token: String, callBack: ApiCallBack<MutableList<String>>) {
        val data = mutableListOf<String>()
        for(i in 0 until 8){
            data.add("飞向太空"+i)
        }
        callBack.onSuccess(data)
    }
}