package com.zhangwan.movieproject.app.ui.mine.item.setting

import com.gxtc.commlibrary.BasePresenter
import com.gxtc.commlibrary.BaseUiView
import com.zhangwan.movieproject.app.bean.UseInfoBean

/**
 * Created by zzg on 2018/4/14.
 */
class SettingContract {
    interface View : BaseUiView<Presenter> {
        fun showUpdateUserInfo(dates: Any?)
        fun showUserInfo(mutableList: UseInfoBean?)
    }

    interface Presenter: BasePresenter {
        fun updateUserInfo(token:String?,nickname:String?, face:String?)
        fun getUserInfo(token:String)
    }
}