package com.zhangwan.movieproject.app.ui.mine.item

import android.os.Bundle
import com.gxtc.commlibrary.base.BaseTitleActivity
import com.zhangwan.movieproject.app.R

/**
 * Created by zzg on 2018/4/14.
 */
class AboutActivity :BaseTitleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quesyion_layout)
    }

    override fun initView() {
        super.initView()
        baseHeadView.showTitle("关于我们").showBackButton {
            finish()
        }
        baseEmptyView.showEmptyContent()
    }
}