package com.zhangwan.movieproject.app.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

/**
 * Created by ALing on 2017/2/23 0023.
 * 倒计时工具类
 */

public class CountDownTimerUtils extends CountDownTimer{
    private Button btnCountDowm;
    private View.OnClickListener mOnClickListener;

    public CountDownTimerUtils(Button btnCountDowm, long millisInFuture, long countDownInterval, View.OnClickListener mOnClickListener) {
        super(millisInFuture, countDownInterval);
        this.btnCountDowm = btnCountDowm;
        this.mOnClickListener = mOnClickListener;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        btnCountDowm.setText("重新发送(" + millisUntilFinished / 1000 + "秒)");
        btnCountDowm.setEnabled(false);
    }

    @Override
    public void onFinish() {
        btnCountDowm.setEnabled(true);
        btnCountDowm.setOnClickListener(mOnClickListener);
        btnCountDowm.setText(" 获取验证码");
    }
}
