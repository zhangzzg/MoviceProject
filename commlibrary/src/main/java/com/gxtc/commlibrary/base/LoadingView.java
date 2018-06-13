package com.gxtc.commlibrary.base;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gxtc.commlibrary.R;


public class LoadingView {

    //private ProgressBar progressBar;
    private ImageView img;
    private FrameLayout baseLoadingArea;

    private static AnimationDrawable anim ;

    public LoadingView(View view) {
        //progressBar = (ProgressBar) view.findViewById(R.id.base_progressBar);
        baseLoadingArea = (FrameLayout) view.findViewById(R.id.base_loading_area);
        img = (ImageView) view.findViewById(R.id.img_waiting);
        anim = (AnimationDrawable) img.getDrawable();
    }

    /**
     * 显示加载背景
     */
    public void showLoading() {
        showLoading(false);
    }

    /**
     * 显示半透明加载背景
     * @param translucenceBg
     */
    public void showLoading(boolean translucenceBg){
        baseLoadingArea.setVisibility(View.VISIBLE);
        if(translucenceBg){
            baseLoadingArea.setBackgroundColor(Color.parseColor("#66000000"));
        }

        if(anim != null){
            anim.start();
        }
    }

    public void hideLoading() {
        baseLoadingArea.setVisibility(View.GONE);
        if(anim != null){
            anim.stop();
        }
    }
}
