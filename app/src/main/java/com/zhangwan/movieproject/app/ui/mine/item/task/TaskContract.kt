package com.zhangwan.movieproject.app.ui.mine.item.task

import com.gxtc.commlibrary.BasePresenter
import com.gxtc.commlibrary.BaseUiView
import com.zhangwan.movieproject.app.bean.TaskBean
import com.zhangwan.movieproject.app.bean.UseInfoBean

/**
 * Created by zzg on 2018/4/14.
 */
class TaskContract {
    interface View : BaseUiView<Presenter> {
        fun showTask(dates: TaskBean?)
    }

    interface Presenter: BasePresenter {
        fun getTask(token:String)
    }
}