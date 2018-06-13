package com.zhangwan.movieproject.app.utils;

public class ClickUtil {
    private static long lastClickTime;
    private static Object sObject;

    /**
     * 防止暴力点击
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 传当前对像
     */
    public static boolean isFastClick(Object obj) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            if (sObject == obj)
                return true;
        }
        lastClickTime = time;
        return false;
    }
}
