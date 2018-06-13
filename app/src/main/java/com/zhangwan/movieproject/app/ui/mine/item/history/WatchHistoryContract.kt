package com.zhangwan.movieproject.app.ui.mine.item.history

import com.gxtc.commlibrary.BasePresenter
import com.gxtc.commlibrary.BaseUiView

/**
 * Created by zzg on 2018/4/14.
 */
class WatchHistoryContract {
    interface View : BaseUiView<Presenter>{
        fun showWatchHistory(mutableList: MutableList<String>?)
    }

    interface Presenter:BasePresenter{
        fun getWatchHistory(token:String)
    }
}