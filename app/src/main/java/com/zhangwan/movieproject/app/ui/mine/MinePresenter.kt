package com.zhangwan.movieproject.app.ui.mine

import com.zhangwan.movieproject.app.bean.UseInfoBean
import com.zhangwan.movieproject.app.http.ApiCallBack
import com.zhangwan.movieproject.app.ui.mine.item.history.WatchHistoryContract

/**
 * Created by zzg on 2018/4/14.
 */
class MinePresenter(view: MineContract.View): MineContract.Presenter {
    var mView: MineContract.View? = null
    var source: MineSource? = null

    init {
        mView = view
        mView?.setPresenter(this)
        source = MineRespostory()
    }

    override fun getUserInfo(token: String) {
        source?.getUserInfo(token,object : ApiCallBack<UseInfoBean>(){
            override fun onSuccess(data:UseInfoBean?) {
                if(data == null ){
                    mView?.showEmpty()
                    return
                }
                mView?.showUserInfo(data)
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