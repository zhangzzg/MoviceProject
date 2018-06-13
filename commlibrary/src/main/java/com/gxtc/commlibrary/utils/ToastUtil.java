package com.gxtc.commlibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtil {

    private static Toast mToast;

    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.show();
        }
    };

    /**
     * 不重复显示Toast 注：显示时间：LENGTH_SHORT
     */
    public static void showShort(Context context, String message) {
        show(context, message, Toast.LENGTH_SHORT, 0, false, 0, 0);
    }

    /**
     * 不重复显示Toast 注：显示时间：LENGTH_LONG
     */
    public static void showLong(Context context, String message) {
        show(context, message, Toast.LENGTH_LONG, 0, false, 0, 0);
    }

    /**
     * 显示一个普通的Toast 注：显示时间：LENGTH_SHORT
     */
    public static void showShort_Repeat(Context context, String message) {
        show(context, message, Toast.LENGTH_SHORT, 0, true, 0, 0);
    }

    /**
     * 显示一个普通的Toast 注：显示时间：LENGTH_LONG
     */
    public static void showLong_Repeat(Context context, String message) {
        show(context, message, Toast.LENGTH_LONG, 0, true, 0, 0);
    }

    // ==================================
    // ==============带图片的Toast===========
    // ==================================

    /**
     * 带图片的Toast&&不重复显示Toast 注
     *
     * @param drawable   显示图片ID，0则不显示
     * @param picGravity 显示图片位置，drawable为0时则不起作用，支持：Gravity.TOP、Gravity.BOTTOM、Gravity.
     *                   LEFT、Gravity.RIGHT
     */
    @SuppressLint("NewApi")
    public static void showShortWithPic(Context context, String message, int drawable, int picGravity) {
        show(context, message, Toast.LENGTH_SHORT, 0, false, drawable, picGravity);
    }

    /**
     * 带图片的Toast&&不重复显示Toast
     *
     * @param drawable   显示图片ID，0则不显示
     * @param picGravity 显示图片位置，drawable为0时则不起作用，支持：Gravity.TOP、Gravity.BOTTOM、Gravity.
     *                   LEFT、Gravity.RIGHT
     */
    public static void showLongWithPic(Context context, String message, int drawable, int picGravity) {
        show(context, message, Toast.LENGTH_LONG, 0, false, drawable, picGravity);
    }

    /**
     * 带图片的Toast&&显示一个普通的Toast
     *
     * @param drawable   显示图片ID，0则不显示
     * @param picGravity 显示图片位置，drawable为0时则不起作用，支持：Gravity.TOP、Gravity.BOTTOM、Gravity.
     *                   LEFT、Gravity.RIGHT
     */
    public static void showShortWithPic_Repeat(Context context, String message, int drawable, int picGravity) {
        show(context, message, Toast.LENGTH_SHORT, 0, true, drawable, picGravity);
    }

    /**
     * 带图片的Toast&&显示一个普通的Toast
     *
     * @param drawable   显示图片ID，0则不显示
     * @param picGravity 显示图片位置，drawable为0时则不起作用，支持：Gravity.TOP、Gravity.BOTTOM、Gravity.
     *                   LEFT、Gravity.RIGHT
     */
    public static void showLongWithPic__Repeat(Context context, String message, int drawable, int picGravity) {
        show(context, message, Toast.LENGTH_LONG, 0, true, drawable, picGravity);
    }

    /**
     * @param message      显示内容
     * @param duration     显示时间Toast.LENGTH_LONG/LENGTH_SHORT
     * @param delayTime    显示延时
     * @param isRepeatShow 是否重复显示Toast
     * @param drawable     显示图片ID，0则不显示
     * @param picGravity   显示图片位置，drawable为0时则不起作用，支持：Gravity.TOP、Gravity.BOTTOM、Gravity.
     *                     LEFT、Gravity.RIGHT
     */
    private static void show(Context context, String message, int duration, int delayTime, boolean isRepeatShow, int drawable, int picGravity) {
        mHandler.removeCallbacks(r);// 因为共用的一个Toast，不移除的话Toast可能会突然不见
        if (mToast != null && !isRepeatShow) // 如果存在且不重复显示 则移除上一个
            mToast.cancel();
        mToast = Toast.makeText(context, message, duration);
        if (drawable != 0) {
            if (picGravity == 0)
                picGravity = Gravity.TOP;
            LinearLayout toastView = (LinearLayout) mToast.getView();
            toastView.setGravity(Gravity.CENTER);
            ImageView imageCodeProject = new ImageView(context);
            imageCodeProject.setImageResource(drawable);
            if (picGravity == Gravity.BOTTOM)
                toastView.addView(imageCodeProject, 1);
            else if (picGravity == Gravity.LEFT) {
                toastView.setOrientation(LinearLayout.HORIZONTAL);
                toastView.addView(imageCodeProject, 0);
            } else if (picGravity == Gravity.RIGHT) {
                toastView.setOrientation(LinearLayout.HORIZONTAL);
                toastView.addView(imageCodeProject, 1);
            } else
                toastView.addView(imageCodeProject, 0);
            mToast.setView(toastView);
        }
        if (delayTime > 0)
            mHandler.postDelayed(r, delayTime);
        else
            mToast.show();
    }

}
