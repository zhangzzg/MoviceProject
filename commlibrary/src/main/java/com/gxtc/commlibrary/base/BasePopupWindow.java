package com.gxtc.commlibrary.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.gxtc.commlibrary.R;

import java.lang.ref.WeakReference;


/**
 * @description:
 * @date: 2016-12-02
 * @time: 15:21
 */
public abstract class BasePopupWindow implements PopupWindow.OnDismissListener, Animation.AnimationListener {

    private static final int INTERVAL = 500;
    private static final int START = 1;
    private static final int END = 2;

    private PopupWindow pWindow;
    private Activity activity;
    private View layoutView;
    private RelativeLayout parentView;        //最顶层的popview

    private MyHandle mHandle;

    private int alpha = 0;


    private boolean outSide = true;         //点击背景是否取消pop
    private boolean transparentBg = false;

    private Animation enterAnim;    //进入动画
    private Animation exitAnim;    //退出动画

    private PopupWindow.OnDismissListener dismissListener;

    public BasePopupWindow(Activity activity, int resId) {
        this(activity, resId, true);
        this.outSide = true;
    }

    public BasePopupWindow(Activity activity, int resId, boolean outSide) {
        this.outSide = outSide;
        parentView = (RelativeLayout) View.inflate(activity, R.layout.base_popwindow_view, null);
        layoutView = LayoutInflater.from(activity).inflate(resId, parentView, false);
        parentView.addView(layoutView);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layoutView.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        this.activity = activity;
        pWindow = new PopupWindow(parentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, outSide);
        pWindow.setAnimationStyle(0);
        pWindow.setOnDismissListener(this);
        pWindow.setBackgroundDrawable(new BitmapDrawable(getActivity().getResources(), (Bitmap) null));
        pWindow.setOutsideTouchable(outSide);

        initListener(parentView);

        init(layoutView);
        initListener();
        loadAnim(loadAnimRes());

        mHandle = new MyHandle(this);
    }


    private void initListener(View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (outSide) {
                    closePop();
                }
            }
        });
    }


    /**
     * ======================================================
     * public api
     * ======================================================
     */

    /**
     * 对view里面的控件初始化
     *
     * @param layoutView 自己传进去的view
     */
    public abstract void init(View layoutView);

    public abstract void initListener();

    /**
     * 显示popwindow
     *
     * @param view 要显示在哪个view下面
     */
    public void showPop(View view) {
        showPop(view, 0, 0);
    }

    /**
     * 显示popwindow
     *
     * @param view    要显示在哪个view下面
     * @param offsetX x轴偏移量
     * @param offsetY y轴偏移量
     */
    public void showPop(View view, int offsetX, int offsetY) {
        showStartBGAnim();
        pWindow.showAsDropDown(view, offsetX, offsetY);
    }

    /**
     * 将popwindow显示到activity中
     *
     * @param activity
     */
    public void showPopOnRootView(Activity activity) {
        //开
        showStartBGAnim();

        pWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        if (enterAnim != null) {
            layoutView.setAnimation(enterAnim);
            enterAnim.start();
        }
    }


    /**
     * 关闭popwindow
     */
    public void closePop() {
        if (exitAnim == null) {
            showEndBgAnim();
            pWindow.dismiss();

        } else {
            layoutView.clearAnimation();
            layoutView.setAnimation(exitAnim);
            exitAnim.start();
        }
    }

    /**
     * 如果要给传入的view添加动画 覆盖此方法
     *
     * @return int [0] 为进入动画   int[1] 退出动画
     */
    protected int[] loadAnimRes() {
        return null;
    }


    public void setTransparentBg() {
        transparentBg = true;
        parentView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
    }


    /**
     * ======================================================
     * private api
     * ======================================================
     */

    private void loadAnim(int[] anims) {
        if (anims != null) {
            enterAnim = AnimationUtils.loadAnimation(activity, anims[0]);
            exitAnim = AnimationUtils.loadAnimation(activity, anims[1]);
            enterAnim.setAnimationListener(this);
            exitAnim.setAnimationListener(this);
        }
    }

    //开始背景动画
    private void showStartBGAnim() {
        if (transparentBg == false && alpha < 255 * 0.5) {
            alpha++;
            parentView.setBackgroundColor(Color.argb(alpha, 000, 000, 000));
            mHandle.sendEmptyMessageAtTime(START, INTERVAL);
        }
    }

    private void showEndBgAnim() {
        if (transparentBg == false && alpha > 0) {
            alpha--;
            parentView.setBackgroundColor(Color.argb(alpha, 000, 000, 000));
            mHandle.sendEmptyMessageAtTime(END, INTERVAL);
        }
    }

    @Override
    public void onDismiss() {
        if (dismissListener != null) {
            dismissListener.onDismiss();
        }

    }


    /**
     * ======================================================
     * 动画监听 回调
     * ======================================================
     */

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == exitAnim) {
            showEndBgAnim();
            pWindow.dismiss();

            if(exitAnim != null && enterAnim != null){
                layoutView.clearAnimation();
                exitAnim.cancel();
                enterAnim.cancel();
            }

        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    static class MyHandle extends Handler {

        private WeakReference<BasePopupWindow> weakView;

        public MyHandle(BasePopupWindow view) {
            weakView = new WeakReference<BasePopupWindow>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BasePopupWindow view = weakView.get();
            if (view == null)
                return;

            switch (msg.what) {
                case START:
                    view.showStartBGAnim();
                    break;
                case END:
                    view.showEndBgAnim();
                    break;
            }
        }
    }



    public void setOnDismissListener(PopupWindow.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }


    //获取进入动画
    public Animation getEnterAnim() {
        return enterAnim;
    }

    //获取退出动画
    public Animation getExitAnim() {
        return exitAnim;
    }

    public View getLayoutView() {
        return layoutView;
    }

    public PopupWindow getpWindow() {
        return pWindow;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
