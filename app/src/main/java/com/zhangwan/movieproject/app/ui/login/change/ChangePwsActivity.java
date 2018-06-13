package com.zhangwan.movieproject.app.ui.login.change;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.gxtc.commlibrary.base.BaseTitleActivity;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.gxtc.commlibrary.utils.WindowUtil;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.ui.login.LoginActivity;
import com.zhangwan.movieproject.app.ui.register.RegisterActivity;
import com.zhangwan.movieproject.app.utils.ClickUtil;
import com.zhangwan.movieproject.app.utils.RegexUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zzg on 2018/4/12
 * 重置密码
 */
public class ChangePwsActivity extends BaseTitleActivity implements View.OnClickListener,ChangeContract.View {

    @BindView(R.id.iv_show_psw)  ImageButton       mIvShowPsw;
    @BindView(R.id.et_password)  TextInputEditText mEtPassword;
    @BindView(R.id.et_yzm)       TextInputEditText mEtYzm;
    @BindView(R.id.btn_findback) Button            mBtnFindback;

    private boolean                     canRead;
    public String codetoken;
    ChangeContract.Presenter presenter ;
    public String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pws);
        initListener();
        initData();
    }

    @Override
    public void initListener() {
        super.initListener();
        codetoken = getIntent().getStringExtra("codetoken");
        phone = getIntent().getStringExtra("tel");
        getBaseHeadView().showTitle("重置密码");
        getBaseHeadView().showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.iv_show_psw, R.id.btn_findback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_show_psw:
                canReadPsw();
                break;
            case R.id.btn_findback:
                findBackPsw();
                break;
        }
    }

    private void findBackPsw() {
        WindowUtil.closeInputMethod(this);
        if (TextUtils.isEmpty(mEtPassword.getText().toString().trim())) {
            ToastUtil.showShort(this, getString(R.string.pwd_canot_empty));
            return;
        }
        if (TextUtils.isEmpty(mEtYzm.getText().toString().trim())) {
            ToastUtil.showShort(this, getString(R.string.yzm_canot_empty));
            return;
        }
        presenter.resetPassWord(phone,mEtPassword.getText().toString(),codetoken,mEtYzm.getText().toString());
    }

    @Override
    public void initData() {
        super.initData();
        new ChangePresenter(this);
        mEtYzm.addTextChangedListener(new YzmTextWatcher(this));
        mEtPassword.addTextChangedListener(new PasswordTextWatcher(this));
    }

    private void canReadPsw() {
        if (canRead) {
            mIvShowPsw.setSelected(true);
            mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            canRead = false;
        } else if (canRead == false) {
            mIvShowPsw.setSelected(false);
            mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            canRead = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEtYzm.removeTextChangedListener(new YzmTextWatcher(this));
        mEtPassword.removeTextChangedListener(new PasswordTextWatcher(this));
        presenter.destroy();
    }

    @Override
    public void showResult(Object datas) {
        startActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void showMobilecode(Object datas) {

    }

    @Override
    public void setPresenter(ChangeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void showLoadFinish() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
          showToast(msg);
    }

    @Override
    public void showNetError() {

    }

    /**
     * 密码输入监听
     */
    static class PasswordTextWatcher implements TextWatcher {
        public ChangePwsActivity                mChangePwsActivity;
        public WeakReference<ChangePwsActivity> mWeakReference;

        public PasswordTextWatcher(ChangePwsActivity mChangePwsActivity) {
            mWeakReference = new WeakReference<>(mChangePwsActivity);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable temp) {
            mChangePwsActivity = mWeakReference.get();
            if(mChangePwsActivity == null) return;
            if ((mChangePwsActivity.mEtPassword.getText().toString().length() >= 6 &&
                    mChangePwsActivity.mEtPassword.getText().toString().length() < 16) || mChangePwsActivity.mEtPassword.getText().toString().length() == 16) {
                mChangePwsActivity.mEtYzm.setEnabled(true);
                mChangePwsActivity.mEtYzm.setEnabled(true);
                mChangePwsActivity.mEtYzm.setFocusable(true);
                mChangePwsActivity.mEtYzm.setFocusableInTouchMode(true);
                mChangePwsActivity.mEtYzm.requestFocus();
            }

        }
    }

    /**
     * 验证码输入监听
     */
    static class YzmTextWatcher implements TextWatcher {
        WeakReference<ChangePwsActivity> mWeakReference;
        ChangePwsActivity mChangePwsActivity;

        public YzmTextWatcher(ChangePwsActivity mChangePwsActivity) {
            mWeakReference = new WeakReference<ChangePwsActivity>(mChangePwsActivity);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            mChangePwsActivity = mWeakReference.get() ;
            if(mChangePwsActivity == null) return;
            if (mChangePwsActivity.mEtYzm.getText().toString().length() == 5) mChangePwsActivity.mBtnFindback.setEnabled(true);
        }
    }

    public static void gotoChangePwsActivity(Context context,String codetoken,String tel){
        Intent intent = new Intent(context,ChangePwsActivity.class);
        intent.putExtra("codetoken",codetoken);
        intent.putExtra("tel",tel);
        context.startActivity(intent);
    }
}
