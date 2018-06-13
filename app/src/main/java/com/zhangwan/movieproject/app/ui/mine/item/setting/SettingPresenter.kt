package com.zhangwan.movieproject.app.ui.mine.item.setting

import com.zhangwan.movieproject.app.bean.UseInfoBean
import com.zhangwan.movieproject.app.http.ApiCallBack
import com.zhangwan.movieproject.app.ui.mine.MineContract
import com.zhangwan.movieproject.app.ui.mine.MineRespostory
import com.zhangwan.movieproject.app.ui.mine.MineSource

/**
 * Created by zzg on 2018/4/14.
 */
class SettingPresenter(view: SettingContract.View): SettingContract.Presenter {
    var mView: SettingContract.View? = null
    var source: MineSource? = null

    init {
        mView = view
        mView?.setPresenter(this)
        source = MineRespostory()
    }

    override fun getUserInfo(token: String) {
        source?.getUserInfo(token,object : ApiCallBack<UseInfoBean>(){
            override fun onSuccess(data: UseInfoBean?) {
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


    override fun updateUserInfo(token: String?, nickname: String?, face: String?) {
        source?.updateUserInfo(token,nickname,face,object : ApiCallBack<Object>(){
            override fun onSuccess(data: Object?) {
                if(data == null ){
                    mView?.showEmpty()
                    return
                }
                mView?.showUpdateUserInfo(data)
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