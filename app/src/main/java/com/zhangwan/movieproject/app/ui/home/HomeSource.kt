package com.zhangwan.movieproject.app.ui.home

import com.gxtc.commlibrary.data.BaseSource
import com.zhangwan.movieproject.app.bean.HomeBean
import com.zhangwan.movieproject.app.http.ApiCallBack

/**
 * Created by zzg on 2018/4/11.
 */

interface HomeSource : BaseSource {
    fun getHomeData(token: String, callBack: ApiCallBack<HomeBean>)
}
