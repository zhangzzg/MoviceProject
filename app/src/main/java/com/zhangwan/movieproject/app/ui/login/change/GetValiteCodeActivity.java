package com.zhangwan.movieproject.app.ui.login.change;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.gxtc.commlibrary.base.BaseTitleActivity;
import com.gxtc.commlibrary.utils.GotoUtil;
import com.gxtc.commlibrary.utils.GsonUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.gxtc.commlibrary.utils.WindowUtil;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.ui.login.LoginActivity;
import com.zhangwan.movieproject.app.ui.register.RegisterActivity;
import com.zhangwan.movieproject.app.ui.register.RegisterContract;
import com.zhangwan.movieproject.app.ui.register.RegsterPresenter;
import com.zhangwan.movieproject.app.utils.ClickUtil;
import com.zhangwan.movieproject.app.utils.CountDownTimerUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zzg on 2018/4/12
 */

public class GetValiteCodeActivity extends BaseTitleActivity implements ChangeContract.View{
    @BindView(R.id.et_phone)      TextInputEditText mEtPhone;
    @BindView(R.id.btn_findback) Button            mBtnFindback;
    ChangeContract.Presenter mPresenter;
    private CountDownTimerUtils mCountDownTimerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_valite_code);
    }

    @Override
    public void initView() {
        super.initView();
        getBaseHeadView().showTitle("获取验证码");
        getBaseHeadView().showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mEtPhone.addTextChangedListener(new MobileTextWatcher(this));
        new ChangePresenter(this);
    }

    @OnClick({R.id.btn_findback})
    public void onClick(View view) {
        if (ClickUtil.isFastClick())    return;
        switch (view.getId()) {
            case R.id.btn_findback:
                if(TextUtils.isEmpty(mEtPhone.getText().toString().trim())){
                    showToast("手机号不能为空");
                    return;
                }
                WindowUtil.closeInputMethod(this);
                mCountDownTimerUtils = new CountDownTimerUtils(mBtnFindback, 60000, 1000, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.getMobilecode(mEtPhone.getText().toString());
                    }
                });
                mCountDownTimerUtils.start();
                mPresenter.getMobilecode(mEtPhone.getText().toString());
                break;
        }
    }


    @Override
    public void showResult(Object datas) {}

    /**
     * 手机号输入监听
     */
    static class MobileTextWatcher implements TextWatcher {
        GetValiteCodeActivity                mGetValiteCodeActivity;
        WeakReference<GetValiteCodeActivity> mWeakReference;

        public MobileTextWatcher(GetValiteCodeActivity mGetValiteCodeActivity) {
            mWeakReference = new WeakReference<>(mGetValiteCodeActivity);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            mGetValiteCodeActivity = mWeakReference.get();
            if(mGetValiteCodeActivity == null) return;
            if (mGetValiteCodeActivity.mEtPhone.getText().toString().length() == 11) {
                mGetValiteCodeActivity.mBtnFindback.setEnabled(true);
            }else {
                mGetValiteCodeActivity.mBtnFindback.setEnabled(false);
            }
        }
    }


    @Override
    public void showMobilecode(Object datas) {
        ToastUtil.showShort(this, getString(R.string.verification_code_send));
        String codetoken = (String) GsonUtil.getJsonValue(GsonUtil.objectToJson(datas),"codetoken");
        ChangePwsActivity.gotoChangePwsActivity(this,codetoken,mEtPhone.getText().toString().trim());
        finish();
    }

    @Override
    public void setPresenter(ChangeContract.Presenter presenter) {
        mPresenter = presenter;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEtPhone.removeTextChangedListener(new MobileTextWatcher(this));
        mCountDownTimerUtils.start();
        mPresenter.destroy();
    }
}
