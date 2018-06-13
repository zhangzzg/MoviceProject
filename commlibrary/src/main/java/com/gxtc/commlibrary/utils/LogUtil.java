package com.gxtc.commlibrary.utils;

import android.util.Log;

import com.gxtc.commlibrary.Constant;

/**
 * @author: lijuan
 * @description: Log统一管理类
 * @date: 2016-11-1
 * @time: 11:09
 */
public class LogUtil {

    public static boolean isDebug = Constant.DEBUG;
    private static final String TAG = "zw";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void printD( String msg) {
        if (isDebug)
            Log.d("print", msg);
    }
}
