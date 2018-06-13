package com.zhangwan.movieproject.app.ui.mine.item.task

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.gxtc.commlibrary.base.BaseTitleActivity
import com.gxtc.commlibrary.utils.SpUtil
import com.zhangwan.movieproject.app.R
import com.zhangwan.movieproject.app.adater.TaskAdater
import com.zhangwan.movieproject.app.bean.TaskBean

import com.zhangwan.movieproject.app.utils.MyDialogUtil
import kotlinx.android.synthetic.main.fragment_find_itemk.*

/**
 * Created by zzg on 2018/4/14.
 */
class TaskActivity : BaseTitleActivity(),TaskContract.View,View.OnClickListener {
    var adater:TaskAdater? = null
    var mPresenter: TaskContract.Presenter? = null
    var tickect_count :TextView? = null
    var vip_count :TextView? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_find_itemk)
    }

    override fun initView() {
        super.initView()
        baseHeadView.showTitle("做任务").showBackButton {
            finish()
        }
        swipe_news.setColorSchemeResources(R.color.refresh_color1,
                R.color.refresh_color2,
                R.color.refresh_color3)
        rl_news.setLoadMoreView(R.layout.model_footview_loadmore)
        rl_news.layoutManager = LinearLayoutManager(this)
        val header = layoutInflater.inflate(R.layout.header_task_layout,null)
        tickect_count = header.findViewById<TextView>(R.id.tickect_count)
        vip_count = header.findViewById<TextView>(R.id.vip_count)
        rl_news.addHeadView(header)
        adater = TaskAdater(this, mutableListOf(),R.layout.task_item_layout)
        rl_news.adapter = adater
    }

    override fun initData() {
        super.initData()
        TaskPresenter(this)
        hideContentLayout()
        mPresenter?.getTask(SpUtil.getToken(this))
    }

    override fun initListener() {
        super.initListener()
        swipe_news?.setOnRefreshListener {
            mPresenter?.getTask(SpUtil.getToken(this))
        }
        adater?.listener = object : View.OnClickListener{
            override fun onClick(v: View?) {
                val bean = v?.tag as TaskBean.Task
                inviteFriends()
            }

        }
    }

    fun inviteFriends(){
        val view  = layoutInflater.inflate(R.layout.invite_friends_dialog_layout,null)
        val wexin = view.findViewById<ImageView>(R.id.weix)
        val pyq = view.findViewById<ImageView>(R.id.pyq)
        val qq = view.findViewById<ImageView>(R.id.qq)
        val qqkongj = view.findViewById<ImageView>(R.id.qqkongj)
        val cancel = view.findViewById<TextView>(R.id.cancel)
        wexin.setOnClickListener(this)
        pyq.setOnClickListener(this)
        qq.setOnClickListener(this)
        qqkongj.setOnClickListener(this)
        val dialog = MyDialogUtil.modifyDialog(this,view)
        cancel.setOnClickListener({
            dialog?.dismiss()
        })
    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.weix ->{
               showToast("微信")
           }
           R.id.pyq ->{
               showToast("朋友圈")
           }
           R.id.qq ->{
               showToast("QQ")
           }
           R.id.qqkongj ->{
               showToast("QQ空间")
           }

       }
    }


    override fun showTask(dates: TaskBean?) {
        showContentLayout()
        swipe_news?.isRefreshing = false
        tickect_count?.text = dates?.count.toString()
        vip_count?.text = dates?.fdate.toString()
        rl_news.notifyChangeData(dates?.rask,adater)
    }

    override fun setPresenter(presenter: TaskContract.Presenter?) {
        mPresenter = presenter
    }

    override fun showLoad() {
        baseLoadingView.showLoading()
    }

    override fun showLoadFinish() {
        baseLoadingView.hideLoading()
    }

    override fun showEmpty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showReLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(code: String?, msg: String?) {
        showToast(msg)
        baseLoadingView.hideLoading()
    }

    override fun showNetError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.destroy()
    }
}