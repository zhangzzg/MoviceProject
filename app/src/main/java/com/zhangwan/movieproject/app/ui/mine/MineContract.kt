package com.zhangwan.movieproject.app.ui.mine

import com.gxtc.commlibrary.BasePresenter
import com.gxtc.commlibrary.BaseUiView
import com.zhangwan.movieproject.app.bean.UseInfoBean

/**
 * Created by zzg on 2018/4/14.
 */
class MineContract {
    interface View : BaseUiView<Presenter> {
        fun showUserInfo(mutableList: UseInfoBean?)
    }

    interface Presenter: BasePresenter {
        fun getUserInfo(token:String)
    }
}