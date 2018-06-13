package com.gxtc.commlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.gxtc.commlibrary.Constant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @description: 页面跳转工具类
 * @author: lijuan
 * @date: 2016-04-18
 * @time: 17:57
 */
public class GotoUtil {
    /**
     * 跳转到另一个页面
     *
     * @param activity
     * @param toClass
     */
    public static void goToActivity(Activity activity, Class<?> toClass) {
        Intent intent = new Intent(activity, toClass);
        activity.startActivity(intent);
    }

    public static void goToActivity(Fragment fragment, Class<?> toClass) {
        Intent intent = new Intent(fragment.getContext(), toClass);
        fragment.startActivity(intent);
    }

    /**
     * 带值跳转到另一个页面
     *
     * @param activity
     * @param toClass
     */
    public static void goToActivity(Activity activity, Class<?> toClass, int requestCode,
                                    Serializable serializable) {
        Intent intent = new Intent(activity, toClass);
        intent.putExtra("data", serializable);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void goToActivity(Fragment fragment, Class<?> toClass, int requestCode,
                                    Serializable serializable) {
        Intent intent = new Intent(fragment.getContext(), toClass);
        intent.putExtra("data", serializable);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 携带值跳转到另一个页面
     *
     * @param activity
     * @param toClass
     * @param map
     */
    public static void goToActivityWithData(Activity activity, Class<?> toClass,
                                            HashMap<String, String> map) {
        Intent intent = new Intent(activity, toClass);
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> temp = (Map.Entry<String, String>) iterator.next();
            intent.putExtra(temp.getKey(), temp.getValue());
        }
        activity.startActivity(intent);
    }

    /**
     * 通过请求码跳转页面
     *
     * @param activity    当前的页面
     * @param toClass     跳转的页面
     * @param requestCode
     */
    public static void goToActivityForResult(Activity activity, Class<?> toClass, int requestCode) {
        Intent intent = new Intent(activity, toClass);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * Fragement 里携带请求码
     *
     * @param fragment
     * @param toClass
     * @param requestCode
     */
    public static void goToActivityForResult(android.support.v4.app.Fragment fragment,
                                             Class<?> toClass, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), toClass);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 携带对象到另一个页面
     *
     * @param activity
     * @param toClass
     * @param
     */
    public static void goToActivityWithBean(Activity activity, Class<?> toClass, Object clazz) {
        Intent intent = new Intent(activity, toClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", (Serializable) clazz);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public static void goToActivityWithDataAndForResult(Activity activity, Class<?> toClass,
                                                        HashMap<String, String> map, int requestCode) {
        Intent intent = new Intent(activity, toClass);
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> temp = (Map.Entry<String, String>) iterator.next();
            intent.putExtra(temp.getKey(), temp.getValue());
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到webview页面
     * @param context
     * @param title
     * @param url
     */
    /*public static void GoToWebViewActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("web_title", title);
        intent.putExtra("web_url", url);
        context.startActivity(intent);

    }*/
}
