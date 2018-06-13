package com.zhangwan.movieproject.app.ui.mine.item.history

import com.zhangwan.movieproject.app.http.ApiCallBack
import com.zhangwan.movieproject.app.ui.mine.MineRespostory
import com.zhangwan.movieproject.app.ui.mine.MineSource

/**
 * Created by zzg on 2018/4/14.
 */
class WatchHistoryPresenter(view: WatchHistoryContract.View): WatchHistoryContract.Presenter {
    var mView: WatchHistoryContract.View? = null
    var source:MineSource? = null

    init {
        mView = view
        mView?.setPresenter(this)
        source = MineRespostory()
    }

    override fun getWatchHistory(token: String) {
        source?.getWatchHistory(token,object :ApiCallBack<MutableList<String>>(){
            override fun onSuccess(data: MutableList<String>?) {
                if(data == null || data.size == 0){
                    mView?.showEmpty()
                    return
                }
                mView?.showWatchHistory(data)
            }

            override fun onError(status: String?, message: String?) {
               mView?.showError(status,message)
            }

        })
    }

    override fun start() {}

    override fun destroy() {
       source?.destroy()
    }
}