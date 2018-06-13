package com.zhangwan.movieproject.app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.gxtc.commlibrary.utils.SpUtil;
import com.zhangwan.movieproject.app.ui.MainActivity;
import com.zhangwan.movieproject.app.ui.login.LoginActivity;

/**
 * Created by zzg on 2018/4/11.
 */
public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                Class clas;
                if(TextUtils.isEmpty(SpUtil.getToken(LaunchActivity.this))){
                    clas =  LoginActivity.class;
                }else {
                    clas =  MainActivity.class;
                }
                intent.setClass(LaunchActivity.this, clas);
                startActivity(intent);
                LaunchActivity.this.finish();
            }
        }, 1000);
    }
}
