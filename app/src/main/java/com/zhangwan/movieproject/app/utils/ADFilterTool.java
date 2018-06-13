package com.zhangwan.movieproject.app.utils;

import android.content.Context;
import android.content.res.Resources;

import com.zhangwan.movieproject.app.R;


/**
 * Created by sjr on 2017/3/20.
 * 判断url是否含有广告
 * http://wangbaiyuan.cn/realization-of-android-webview-advertising-filtering.html
 */

public class ADFilterTool {
    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }
}
