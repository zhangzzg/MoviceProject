package com.zhangwan.movieproject.app.adater

import android.content.Context
import android.view.View
import android.widget.CheckBox
import com.gxtc.commlibrary.base.BaseRecyclerAdapter
import com.gxtc.commlibrary.utils.ToastUtil
import com.zhangwan.movieproject.app.R
import com.zhangwan.movieproject.app.bean.TaskBean

/**
 * Created by zzg on 2018/4/14.
 */
class TaskAdater(context: Context, datas:MutableList<TaskBean.Task>, res:Int): BaseRecyclerAdapter<TaskBean.Task>(context,datas,res) {
    var listener:View.OnClickListener? = null

    override fun bindData(holder: ViewHolder?, position: Int, bean: TaskBean.Task?) {
        holder?.setText(R.id.task_note,bean?.name)
        holder?.setText(R.id.task_content,bean?.desc)
        holder?.getView(R.id.do_it)?.setOnClickListener { it
            it.tag = bean
            listener?.onClick(it)
       }
    }
}