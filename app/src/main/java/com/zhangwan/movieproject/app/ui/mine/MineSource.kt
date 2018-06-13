package com.zhangwan.movieproject.app.ui.mine

import com.gxtc.commlibrary.data.BaseSource
import com.zhangwan.movieproject.app.bean.TaskBean
import com.zhangwan.movieproject.app.bean.UseInfoBean
import com.zhangwan.movieproject.app.http.ApiCallBack

/**
 * Created by zzg on 2018/4/14.
 */
interface MineSource :BaseSource {
    fun getUserInfo(token:String,callBack: ApiCallBack<UseInfoBean>)
    fun getTask(token:String,callBack: ApiCallBack<TaskBean>)
    fun getWatchHistory(token:String,callBack: ApiCallBack<MutableList<String>>)
    fun updateUserInfo(token:String?,nickname:String?, face:String?,callBack: ApiCallBack<Object>)
}