package com.zhangwan.movieproject.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.gxtc.commlibrary.utils.ActivityController;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.movieproject.app.ui.login.LoginActivity;

/**
 * Created by zzg on 2018/4/24
 */

public class ErroreUtil {

    public static void handleErrore(Activity activity,String code, String msg){
        //4002 token过期错误码
        if("4002".equals(code)){
            ReloginTodo(activity);
        }else {
            ToastUtil.showShort(activity,msg);
        }
    }


    //重新登录（因为token过期）需要把之前的数据清除，重新获取
    private static void ReloginTodo(final Activity activity){
        MyDialogUtil.noticeDialog(activity, "token过期，需要重新登录", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpUtil.exitSystemt(activity);
                ActivityController.getInstant().finishAllActivity();
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        });

    }
}
