package com.zhangwan.movieproject.app.ui.mine.item.history

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.gxtc.commlibrary.base.BaseTitleActivity
import com.gxtc.commlibrary.utils.SpUtil
import com.gxtc.commlibrary.utils.ToastUtil
import com.zhangwan.movieproject.app.R
import com.zhangwan.movieproject.app.adater.WatchHistoryAdater
import kotlinx.android.synthetic.main.watch_history_layout.*
import rx.Observable
import rx.functions.Action1

/**
 * Created by zzg on 2018/4/14.
 */
class WatchHistoryActivity : BaseTitleActivity(), WatchHistoryContract.View {

    var adater: WatchHistoryAdater? = null
    var mPresenter: WatchHistoryContract.Presenter? = null
    var datas: MutableList<String>? = null
    var mPositionList: MutableList<String>? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.watch_history_layout)
    }

    override fun initView() {
        super.initView()
        baseHeadView.showTitle("观看记录").showBackButton {
            finish()
        }
        baseHeadView.showHeadRightButton("编辑",{
            if(adater?.isEdit!!){
                baseHeadView.headRightButton.text = "编辑"
                adater?.isEdit = false
                delete.visibility = View.GONE
            }else{
                baseHeadView.headRightButton.text = "取消"
                adater?.isEdit = true
                delete.visibility = View.VISIBLE
            }
            recyclerView.notifyChangeData(datas,adater)
        })
        refreshlayout.setColorSchemeResources(R.color.refresh_color1,
                R.color.refresh_color2,
                R.color.refresh_color3)
        recyclerView.setLoadMoreView(R.layout.model_footview_loadmore)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adater = WatchHistoryAdater(this, mutableListOf(),R.layout.watch_history_item_layout)
        recyclerView.adapter = adater
    }

    override fun initListener() {
        super.initListener()
        refreshlayout.setOnRefreshListener {
            adater?.isEdit = false
            mPresenter?.getWatchHistory(SpUtil.getToken(this))
        }
        recyclerView.setOnLoadMoreListener {
            recyclerView.reLoadFinish()
            mPresenter?.getWatchHistory(SpUtil.getToken(this))
        }
        delete.setOnClickListener{
          deleteData()
        }
        adater?.listener = object : WatchHistoryAdater.PositionListenner{
            override fun getPositionList(positionList: MutableList<String>?) {
                mPositionList = positionList;
            }

        }

    }

    private fun deleteData() {
      if(mPositionList == null || mPositionList?.size == 0) {
          showToast("请先选择要删除的记录")
          return
      }
        Observable.from(mPositionList).subscribe(Action1 { p ->
            datas?.remove(p)
        })
        recyclerView.notifyChangeData(datas,adater)
        adater?.positionList?.clear()
        mPositionList?.clear()
        if(adater?.list!!.size == 0){
            delete.visibility = View.GONE
            baseHeadView.headRightButton.visibility = View.INVISIBLE
        }
    }

    override fun initData() {
        super.initData()
        WatchHistoryPresenter(this)
        mPresenter?.getWatchHistory(SpUtil.getToken(this))
    }


    override fun showWatchHistory(mutableList: MutableList<String>?) {
        if(adater?.isEdit!!){
            baseHeadView.headRightButton.text = "取消"
        }else{
            baseHeadView.headRightButton.text = "编辑"
        }
        baseHeadView.headRightButton.visibility = View.VISIBLE
        refreshlayout.isRefreshing = false
        datas = mutableList
        recyclerView.notifyChangeData(mutableList,adater)
    }

    override fun setPresenter(presenter: WatchHistoryContract.Presenter?) {
        mPresenter = presenter
    }

    override fun showLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadFinish() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmpty() {
        baseHeadView.headRightButton.visibility = View.INVISIBLE
        baseEmptyView.showEmptyContent()
    }

    override fun showReLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(code: String?, msg: String?) {
        refreshlayout.isRefreshing = false
        baseHeadView.headRightButton.visibility = View.INVISIBLE
        showToast(msg)
    }

    override fun showNetError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.destroy()
    }
}