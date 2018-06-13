package com.zhangwan.movieproject.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.utils.WindowUtil;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.ui.register.RegisterActivity;

/**
 * 弹窗
 * Created by zzg on 2018/4/16
 */

public class MyDialogUtil {

    private static Dialog myDialog;

    private Context mContext;

    public MyDialogUtil(Context mContext) {
        this.mContext = mContext;
    }

    //关闭窗口
    public static Dialog dissmiss() {
        myDialog.dismiss();
        return myDialog;
    }

    //兑换书券弹窗
    public static Dialog commentDialog(Context context, View view) {
        myDialog = new Dialog(context, R.style.common_dialog);
        myDialog.setContentView(view);
        myDialog.show();
        myDialog.setCanceledOnTouchOutside(true);
        Window window = myDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (WindowUtil.getScreenW(context) * 0.7);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        return myDialog;
    }

    public static Dialog modifyDialog(Context context, View view) {
        myDialog = new Dialog(context, R.style.common_dialog);
        myDialog.setContentView(view);
        myDialog.show();
        myDialog.setCanceledOnTouchOutside(true);
        Window window = myDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (WindowUtil.getScreenW(context) * 0.8);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        return myDialog;
    }

    public static Dialog noticeDialog(Context context,String title, final View.OnClickListener listener) {
        myDialog = new Dialog(context, R.style.common_dialog);
        View view = View.inflate(context,R.layout.commom_note_dialog_layout,null);
        myDialog.setContentView(view);
        myDialog.show();
        myDialog.setCanceledOnTouchOutside(true);
        Window window = myDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (WindowUtil.getScreenW(context) * 0.8);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        TextView textViewTitle = view.findViewById(R.id.title);
        textViewTitle.setText(title);
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        view.findViewById(R.id.confrom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                myDialog.dismiss();
            }
        });
        return myDialog;
    }

}
