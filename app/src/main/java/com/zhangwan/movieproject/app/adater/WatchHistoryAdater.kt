package com.zhangwan.movieproject.app.adater

import android.content.Context
import android.view.View
import android.widget.CheckBox
import com.gxtc.commlibrary.base.BaseRecyclerAdapter
import com.zhangwan.movieproject.app.R
import java.security.AccessControlContext

/**
 * Created by zzg on 2018/4/14.
 */
class WatchHistoryAdater(context: Context,datas:MutableList<String>,res:Int): BaseRecyclerAdapter<String>(context,datas,res) {
    var isEdit :Boolean = false
    var listener:PositionListenner? = null
    var positionList:MutableList<String>? = null

    interface PositionListenner{
        fun getPositionList(positionList:MutableList<String>?)
    }

    init {
        positionList = mutableListOf()
    }

    override fun bindData(holder: ViewHolder?, position: Int, bean: String?) {
        holder?.setText(R.id.tv_name,bean)
        val  mCheckBox = holder?.getView(R.id.iv_doc) as CheckBox
        mCheckBox.isChecked = false
        if(!isEdit){
            holder?.getView(R.id.iv_doc)?.visibility = View.GONE
        }else{
            holder?.getView(R.id.iv_doc)?.visibility = View.VISIBLE

        }
        mCheckBox.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                if(mCheckBox.isChecked){
                    positionList?.add(bean!!)
                }else{
                    positionList?.remove(bean)
                }
                listener?.getPositionList(positionList)
            }

        })
        }
    }