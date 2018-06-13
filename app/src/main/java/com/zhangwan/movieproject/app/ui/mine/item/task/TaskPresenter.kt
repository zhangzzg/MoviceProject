package com.zhangwan.movieproject.app.ui.mine.item.task

import com.zhangwan.movieproject.app.bean.TaskBean
import com.zhangwan.movieproject.app.http.ApiCallBack
import com.zhangwan.movieproject.app.ui.mine.MineRespostory
import com.zhangwan.movieproject.app.ui.mine.MineSource
import com.zhangwan.movieproject.app.ui.mine.item.history.WatchHistoryContract

/**
 * Created by zzg on 2018/4/14.
 */
class TaskPresenter(view: TaskContract.View): TaskContract.Presenter {

    var mView: TaskContract.View? = null
    var source: MineSource? = null

    init {
        mView = view
        mView?.setPresenter(this)
        source = MineRespostory()
    }

    override fun getTask(token: String) {
        mView?.showLoad()
        source?.getTask(token,object : ApiCallBack<TaskBean>(){
            override fun onSuccess(data: TaskBean?) {
                mView?.showLoadFinish()
                if(data == null ){
                    mView?.showEmpty()
                    return
                }
                mView?.showTask(data)
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