package com.zhangwan.movieproject.app.ui.mine.item.setting

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.gxtc.commlibrary.base.BaseTitleActivity
import com.gxtc.commlibrary.helper.ImageHelper
import com.gxtc.commlibrary.utils.ActivityController
import com.gxtc.commlibrary.utils.SpUtil
import com.gxtc.commlibrary.utils.ToastUtil
import com.zhangwan.movieproject.app.R
import com.zhangwan.movieproject.app.bean.UseInfoBean
import com.zhangwan.movieproject.app.ui.login.LoginActivity
import com.zhangwan.movieproject.app.utils.CommonUtil
import com.zhangwan.movieproject.app.utils.MyDialogUtil
import kotlinx.android.synthetic.main.setting_layout.*

/**
 * Created by zzg on 2018/4/14.
 */
class SettingActivity : BaseTitleActivity() , View.OnClickListener,SettingContract.View {

    var mPresenter: SettingContract.Presenter? = null
    var name:String? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_layout)
    }

    override fun initView() {
        super.initView()
        baseHeadView.showTitle("设置").showBackButton {
            if(!TextUtils.isEmpty("name")){
                setResult(Activity.RESULT_OK)
            }
            finish()
        }
    }

    override fun initListener() {
        super.initListener()
        rl_info_pic.setOnClickListener(this)
        rl_info_nickname.setOnClickListener(this)
        exit.setOnClickListener(this)
    }


    override fun initData() {
        super.initData()
        SettingPresenter(this)
        mPresenter?.getUserInfo(SpUtil.getToken(this))
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rl_info_pic ->{
                ToastUtil.showShort(this,"头像")
            }
            R.id.rl_info_nickname ->{
                val view = layoutInflater.inflate(R.layout.alername_layout,null)
                val content = view.findViewById<EditText>(R.id.content)
                val cancel = view.findViewById<TextView>(R.id.cancel)
                val commit = view.findViewById<TextView>(R.id.commit)
                val dialog = MyDialogUtil.commentDialog(this,view)
                cancel.setOnClickListener{
                    dialog.dismiss()
                }
                commit.setOnClickListener{
                    if(TextUtils.isEmpty(content.text.toString().trim())){
                        showToast("昵称不能为空")
                    }else{
                        name = content.text.toString()
                        mPresenter?.updateUserInfo(SpUtil.getToken(this),content.text.toString(),null)
                        dialog.dismiss()
                    }
                }

            }
            R.id.exit ->{
                exit()
            }
        }
    }

    fun exit(){
        val v = getLayoutInflater().inflate(R.layout.commom_note_dialog_layout, null)
        val mDialog = MyDialogUtil.commentDialog(this, v)
        v.findViewById<TextView>(R.id.title).text = "确定退出?"
        v.findViewById<TextView>(R.id.cancel).setOnClickListener(View.OnClickListener { mDialog.dismiss() })
        v.findViewById<TextView>(R.id.confrom).setOnClickListener(View.OnClickListener {
            SpUtil.exitSystemt(this)
            mDialog.dismiss()
            ActivityController.getInstant().finishAllActivity()
            startActivity(LoginActivity::class.java)
        })

    }

    override fun showUpdateUserInfo(dates: Any?) {
        if(!TextUtils.isEmpty(name)){
            tv_info_nickname.text = name
            SpUtil.setUserName(this,name)
        }
    }

    override fun setPresenter(presenter: SettingContract.Presenter?) {
        mPresenter = presenter
    }

    override fun showUserInfo(datas: UseInfoBean?) {
        tv_info_nickname.text = datas?.nickname
        userid.text = "ID("+datas?.unique+")"
        if(!TextUtils.isEmpty(datas?.wnickname)){
            wx_alername.text ="账号微信("+datas?.wnickname+")"
        }
        if(!this.isFinishing) ImageHelper.loadImage(this,datas?.face,iv_info_pic)
    }

    override fun showLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadFinish() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmpty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showReLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(code: String?, msg: String?) {
        showToast(msg)
    }

    override fun showNetError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}