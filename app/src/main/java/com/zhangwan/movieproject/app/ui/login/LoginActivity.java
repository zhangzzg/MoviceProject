package com.zhangwan.movieproject.app.ui.login;

import android.os.Build;
import android.support.design.widget.TextInputEditText;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gxtc.commlibrary.base.BaseTitleActivity;
import com.gxtc.commlibrary.utils.GotoUtil;
import com.gxtc.commlibrary.utils.GsonUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.gxtc.commlibrary.utils.WindowUtil;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.ui.MainActivity;
import com.zhangwan.movieproject.app.ui.login.change.GetValiteCodeActivity;
import com.zhangwan.movieproject.app.ui.register.RegisterActivity;
import com.zhangwan.movieproject.app.utils.ClickUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zzg on 2018/4/11.
 */
public class LoginActivity extends BaseTitleActivity implements LoginContract.View{

    @BindView(R.id.et_count)       TextInputEditText mEtCount;
    @BindView(R.id.et_password)   TextInputEditText mEtPassword;
    @BindView(R.id.btn_login)     Button            mBtnLogin;
    @BindView(R.id.tv_forget_psw) TextView          mTvForgetPsw;

    LoginContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        super.initView();
        getBaseHeadView().showTitle("账号登录").showHeadRightButton("注册", new OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(RegisterActivity.class);
            }
        });
        getBaseHeadView().showCancelBackButton("跳过", new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.tempLogin();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mEtCount.addTextChangedListener(new MobileTextWatcher());
        mEtPassword.addTextChangedListener(new PasswordTextWatcher());
        new LoginPresenter(this);
    }

    @OnClick({R.id.btn_login, R.id.tv_forget_psw,R.id.iv_qq,R.id.iv_webchat})
    public void onClick(View view) {
        if (ClickUtil.isFastClick())    return;
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_forget_psw:
                GotoUtil.goToActivity(this, GetValiteCodeActivity.class);
                finish();
                break;
            case R.id.iv_qq:
                ToastUtil.showShort(this,"QQ登录");
                break;
            case R.id.iv_webchat:
                ToastUtil.showShort(this,"微信登录");
                break;
        }
    }

    private void login() {
        WindowUtil.closeInputMethod(this);
        if (TextUtils.isEmpty(mEtCount.getText().toString().trim())) {
            ToastUtil.showShort(this, getString(R.string.count_canot_empty));
        } else if (mEtCount.getText().toString().trim().length() > 11) {
            ToastUtil.showShort(this, getString(R.string.incorrect_account_format));
        } else if (TextUtils.isEmpty(mEtPassword.getText().toString())) {
            ToastUtil.showShort(this, getString(R.string.pwd_canot_empty));
        } else if (mEtPassword.getText().length() < 6) {
            ToastUtil.showShort(this, getString(R.string.toast_password_length));
        } else {
            mPresenter.login(mEtCount.getText().toString(), mEtPassword.getText().toString());
        }
    }

    //手机登录成功接口返回
    @Override
    public void showLoginDate(Object datas) {
        String token = (String) GsonUtil.getJsonValue(GsonUtil.objectToJson(datas),"token");
        SpUtil.setToken(this,token);
        String  grade = (String) GsonUtil.getJsonValue(GsonUtil.objectToJson(datas),"grade");
        SpUtil.setGrade(this,grade);
        startActivity(MainActivity.class);
        finish();
    }

    //qq登录成功接口返回
    @Override
    public void showWeChatDate(Object datas) {

    }

    //微信登录成功接口返回
    @Override
    public void showQQDate(Object datas) {

    }

    //临时登录成功回调
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
    public void setPresenter(LoginContract.Presenter presenter) {
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
     class MobileTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mEtCount.getText().toString().length() == 11) {
                mEtPassword.setEnabled(true);
                mEtPassword.setFocusable(true);
                mEtPassword.setFocusableInTouchMode(true);
                mEtPassword.requestFocus();
            } else {
                mEtPassword.setEnabled(false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    /**
     * 密码输入监听
     */
    class PasswordTextWatcher implements TextWatcher {
        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if ((temp.length() >= 6 && temp.length() < 16) || temp.length() == 16) {
                mBtnLogin.setEnabled(true);
                if (temp.length() == 16) try {
                    WindowUtil.closeInputMethod(LoginActivity.this);
                } catch (Exception e) {
                    return;
                }
            } else mBtnLogin.setEnabled(false);

        }
    }
}

