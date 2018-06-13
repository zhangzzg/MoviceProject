package com.zhangwan.movieproject.app.ui.home

import com.gxtc.commlibrary.BasePresenter
import com.gxtc.commlibrary.BaseUiView
import com.zhangwan.movieproject.app.bean.HomeBean

/**
 * Created by zzg on 2018/4/11.
 */

class HomeContract {
     interface View : BaseUiView<Presenter> {
        fun showHomeData(data: HomeBean)
    }

     interface Presenter : BasePresenter {
        fun getHomeData(token: String)
    }
}
