package com.zhangwan.movieproject.app.ui.register;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gxtc.commlibrary.base.BaseTitleActivity;
import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.GsonUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.gxtc.commlibrary.utils.WindowUtil;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.ui.MainActivity;
import com.zhangwan.movieproject.app.utils.ClickUtil;
import com.zhangwan.movieproject.app.utils.CountDownTimerUtils;
import com.zhangwan.movieproject.app.utils.RegexUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * Created by zzg on 2018/4/11.
 */
public class RegisterActivity extends BaseTitleActivity implements RegisterContract.View,View.OnClickListener {
    @BindView(R.id.et_register_phone)   TextInputEditText mEtRegisterPhone;
    @BindView(R.id.et_register_yzm)      TextInputEditText mEtRegisterYzm;
    @BindView(R.id.btn_send_yzm)         Button            mBtnSendYzm;
    @BindView(R.id.iv_show_pws)          ImageButton       mIvShowPws;
    @BindView(R.id.et_register_password) TextInputEditText mEtRegisterPassword;
    @BindView(R.id.btn_register)         Button            mBtnRegister;
    @BindView(R.id.cb_register_read)     TextView          mCbRegisterRead;
    @BindView(R.id.tv_register_xy)       TextView          mTvRegisterXy;


    RegisterContract.Presenter mPresenter;
    public String codetoken;
    private boolean canRead = true;
    private CountDownTimerUtils mCountDownTimerUtils;
    private TextWatcher yzmTextWatcher;
    private TextWatcher mobileTextWatcher;
    private TextWatcher passwordTextWatcher;
    public boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initView() {
        super.initView();
        getBaseHeadView().showTitle("账号注册").showBackButton( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //从首页八大平道跳转过来的,则隐藏跳过
        if(flag){
            getBaseHeadView().showHeadRightButton("跳过", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.tempLogin();
                }
            });
        }
        yzmTextWatcher = new YzmTextWatcher(this);
        mobileTextWatcher = new MobileTextWatcher(this);
        passwordTextWatcher = new PasswordTextWatcher(this);
        mEtRegisterPhone.addTextChangedListener(mobileTextWatcher);
        mEtRegisterYzm.addTextChangedListener(yzmTextWatcher);
        mEtRegisterPassword.addTextChangedListener(passwordTextWatcher);
    }

    @Override
    public void initData() {
        super.initData();
        new RegsterPresenter(this);
    }

    @OnClick({R.id.btn_send_yzm,
            R.id.btn_register,
            R.id.iv_show_pws,
            R.id.tv_register_xy})
    public void onClick(View view) {
        if (ClickUtil.isFastClick())    return;
        switch (view.getId()) {
            case R.id.btn_send_yzm:
//                mBtnSendYzm.setOnClickListener(null);
                //发送验证码
                if (TextUtils.isEmpty(mEtRegisterPhone.getText().toString().trim())) {
                    ToastUtil.showShort(this, getString(R.string.count_canot_empty));
                    return;
                }
                mPresenter.getMobilecode(mEtRegisterPhone.getText().toString());
                break;

            case R.id.btn_register:
                WindowUtil.closeInputMethod(this);
                if (TextUtils.isEmpty(mEtRegisterPhone.getText().toString())) {
                    ToastUtil.showShort(this, getString(R.string.count_canot_empty));
                    return;
                }
                if (!RegexUtils.isMobileSimple(mEtRegisterPhone.getText().toString())) {
                    ToastUtil.showShort(this, getString(R.string.incorrect_phone_format));
                    return;
                }
                if (TextUtils.isEmpty(mEtRegisterYzm.getText().toString())) {
                    ToastUtil.showShort(this,
                            getString(R.string.yzm_canot_empty));
                    return;
                }
                if (TextUtils.isEmpty(mEtRegisterPassword.getText().toString())) {
                    ToastUtil.showShort(this, getString(R.string.pwd_canot_empty));
                    return;
                }
                mPresenter.register(mEtRegisterPhone.getText().toString(),mEtRegisterPassword.getText().toString(),codetoken,mEtRegisterYzm.getText().toString());
                break;

            case R.id.iv_show_pws:
                canReadPsd();
                break;

            case R.id.tv_register_xy:

                break;
        }
    }

    private void canReadPsd() {
        if (canRead) {
            mIvShowPws.setSelected(true);
            mEtRegisterPassword.setTransformationMethod(
                    HideReturnsTransformationMethod.getInstance());
            canRead = false;
        } else if (canRead == false) {
            mIvShowPws.setSelected(false);
            mEtRegisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            canRead = true;
        }
    }


    @Override
    public void showRegisterDate(Object datas) {
        String token = (String) GsonUtil.getJsonValue(GsonUtil.objectToJson(datas),"token");
        SpUtil.setToken(this,token);
        String  grade = (String) GsonUtil.getJsonValue(GsonUtil.objectToJson(datas),"grade");
        SpUtil.setGrade(this,grade);
        finish();
    }

    @Override
    public void showMobilecode(Object datas) {
         codetoken = (String) GsonUtil.getJsonValue(GsonUtil.objectToJson(datas),"codetoken");
         ToastUtil.showShort(this, getString(R.string.verification_code_send));
         mCountDownTimerUtils = new CountDownTimerUtils(mBtnSendYzm, 60000, 1000,this);
         mCountDownTimerUtils.start();
    }

    @Override
    public void showTempLoginResulte(Object datas) {
        String  tempToken = (String) GsonUtil.getJsonValue(GsonUtil.objectToJson(datas),"token");
        String  grade = (String) GsonUtil.getJsonValue(GsonUtil.objectToJson(datas),"grade");
        SpUtil.setToken(this,tempToken);
        SpUtil.setGrade(this,grade);
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoad() {
       getBaseLoadingView().showLoading();
    }

    @Override
    public void showLoadFinish() {
        getBaseLoadingView().hideLoading();
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
       ToastUtil.showShort(this,msg);
    }

    @Override
    public void showNetError() {

    }

    /**
     * 手机号输入监听
     */
    static class MobileTextWatcher implements TextWatcher {
        RegisterActivity                mRegisteFragment;
        WeakReference<RegisterActivity> mWeakReference;

        public MobileTextWatcher(RegisterActivity registeFragment) {
            mWeakReference = new WeakReference<>(registeFragment);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            mRegisteFragment = mWeakReference.get();
            if(mRegisteFragment == null) return;
            if (mRegisteFragment.mEtRegisterPhone.getText().toString().length() == 11) {
                mRegisteFragment.mBtnSendYzm.setEnabled(true);
                mRegisteFragment.mEtRegisterYzm.setEnabled(true);
                mRegisteFragment.mEtRegisterYzm.setFocusable(true);
                mRegisteFragment.mEtRegisterYzm.setFocusableInTouchMode(true);
                mRegisteFragment.mEtRegisterYzm.requestFocus();
                try {
                    WindowUtil.closeInputMethod(mRegisteFragment);
                } catch (Exception e) {
                    return;
                }

            } else {
                mRegisteFragment.mBtnSendYzm.setEnabled(false);
                mRegisteFragment.mEtRegisterYzm.setEnabled(false);
            }
        }
    }

    /**
     * 验证码输入监听
     */
    static class YzmTextWatcher implements TextWatcher {
        RegisterActivity mRegisteFragment;
        WeakReference<RegisterActivity> mWeakReference;

        public YzmTextWatcher(RegisterActivity registeFragment) {
            mWeakReference = new WeakReference<>(registeFragment);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mRegisteFragment = mWeakReference.get();
            if(mRegisteFragment == null) return;
            if (mRegisteFragment.mEtRegisterPhone.length() < 11) {
                mRegisteFragment.mEtRegisterYzm.setEnabled(false);
                return;
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (mRegisteFragment.mEtRegisterYzm.getText().toString().length() == 5){
                mRegisteFragment.mEtRegisterPassword.setEnabled(true);
                mRegisteFragment.mEtRegisterPassword.setFocusable(true);
            }

        }
    }

    /**
     * 密码输入监听
     */
    static class PasswordTextWatcher implements TextWatcher {
        public CharSequence temp;
        public RegisterActivity mRegisteFragment;
        public WeakReference<RegisterActivity> mWeakReference;

        public PasswordTextWatcher(RegisterActivity registeFragment) {
            mWeakReference = new WeakReference<>(registeFragment);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            mRegisteFragment = mWeakReference.get();
            if(mRegisteFragment == null) return;
            if ((temp.length() >= 6 && temp.length() < 16) || temp.length() == 16) {
                mRegisteFragment.mBtnRegister.setEnabled(true);
                if (temp.length() == 16) try {
                    WindowUtil.closeInputMethod(mRegisteFragment);
                } catch (Exception e) {
                    return;
                }
            } else mRegisteFragment.mBtnRegister.setEnabled(false);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        if(mEtRegisterPhone != null){
            mEtRegisterPhone.removeTextChangedListener(mobileTextWatcher);
        }
        if(mEtRegisterYzm != null){
            mEtRegisterYzm.removeTextChangedListener(yzmTextWatcher);
        }
        if(mEtRegisterPassword != null){
            mEtRegisterPassword.removeTextChangedListener(passwordTextWatcher);
        }
        if(mCountDownTimerUtils != null) mCountDownTimerUtils.cancel();
    }
}
