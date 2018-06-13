package com.zhangwan.movieproject.app.ui.home

import android.os.Parcel
import android.os.Parcelable
import com.gxtc.commlibrary.BasePresenter
import com.zhangwan.movieproject.app.bean.HomeBean
import com.zhangwan.movieproject.app.http.ApiCallBack

/**
 * Created by zzg on 2018/4/11.
 */

 class HomePresenter(view: HomeContract.View): HomeContract.Presenter {
    var mView: HomeContract.View? = null
    var mSource: HomeSource

    init {
        mView = view
        mView?.setPresenter(this)
        mSource = HomeRespository()
    }

    override fun getHomeData(token: String) {
        mView!!.showLoad()
        mSource.getHomeData(token, object : ApiCallBack<HomeBean>() {

            override fun onSuccess(data: HomeBean?) {
                mView!!.showLoadFinish()
                if (mView == null) return
                if (data == null) {
                    mView!!.showEmpty()
                    return
                }
                mView!!.showHomeData(data)
            }

            override fun onError(status: String, message: String) {
                if (mView == null) return
                mView!!.showError(status, message)
            }
        })
    }

    override fun start() {}

    override fun destroy() {
        mSource.destroy()
    }

}
