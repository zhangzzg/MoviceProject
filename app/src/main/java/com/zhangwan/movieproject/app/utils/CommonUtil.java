package com.zhangwan.movieproject.app.utils;

import android.content.Context;
import android.os.Environment;

import com.gxtc.commlibrary.utils.ToastUtil;

import static com.gxtc.commlibrary.utils.FileUtil.deleteDir;

/**
 * Created by zzg on 2018/4/20.
 */

public class CommonUtil {
    /**
     * 清除缓存
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
        ToastUtil.showShort(context,"清除成功");
    }

}
