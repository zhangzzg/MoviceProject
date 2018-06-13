package com.gxtc.commlibrary.base;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.R;


public class EmptyView {

    private Button baseReload;
    private TextView emptyTextView;
    private ImageView emptyImageView;
    private FrameLayout baseEmptyArea;

    public EmptyView(View view) {
        baseReload = (Button) view.findViewById(R.id.base_reload);
        emptyImageView = (ImageView) view.findViewById(R.id.emptyImageView);
        emptyTextView = (TextView) view.findViewById(R.id.emptyTextView);
        baseEmptyArea = (FrameLayout) view.findViewById(R.id.base_empty_area);

//        emptyImageView.setVisibility(View.GONE);
        baseReload.setVisibility(View.GONE);
        baseEmptyArea.setVisibility(View.GONE);
    }


    public View getBaseEmptyView() {
        return baseEmptyArea;
    }

    public void hideEmptyView() {
        baseEmptyArea.setVisibility(View.GONE);
    }

    public void setReloadOnClick(View.OnClickListener listener) {
        baseReload.setOnClickListener(listener);
        baseReload.setVisibility(View.VISIBLE);
    }

    public void setReloadOnClick(String text, View.OnClickListener listener) {
        baseReload.setOnClickListener(listener);
        baseReload.setText(text);
        baseReload.setVisibility(View.VISIBLE);

    }

    public void setBackground(int res) {
        baseEmptyArea.setBackgroundColor(res);
    }

    public void hideButton() {
        baseReload.setVisibility(View.GONE);

    }

    /**
     * 显示网络错误页面
     *
     * @param imageRes
     * @param textRes
     */
    public void showNetWorkView(int imageRes, int textRes, int btnTextRes, View.OnClickListener listener) {
        emptyImageView.setImageResource(imageRes);
        emptyImageView.setVisibility(View.VISIBLE);
        emptyTextView.setText(textRes);
        if (listener != null) {
            baseReload.setOnClickListener(listener);
            baseReload.setText(btnTextRes);
            baseReload.setVisibility(View.VISIBLE);
        }
        showEmptyView();
    }

    public void showNetWorkView(View.OnClickListener listener) {
        emptyImageView.setImageResource(R.drawable.empty_no_network);
        emptyImageView.setVisibility(View.VISIBLE);
        emptyTextView.setText("无法连接网络");
        if (listener != null) {
            baseReload.setOnClickListener(listener);
            baseReload.setText("重试");
            baseReload.setVisibility(View.VISIBLE);
        }
        showEmptyView();
    }

    public void showNetWorkViewReload(View.OnClickListener listener) {
        emptyImageView.setImageResource(R.drawable.empty_no_network);
        emptyImageView.setVisibility(View.VISIBLE);
        emptyTextView.setText("轻触屏幕重新加载");
        if (listener != null) {
            baseEmptyArea.setOnClickListener(listener);
        }
        showEmptyView();
    }

    public void showEmptyView() {
        baseEmptyArea.setVisibility(View.VISIBLE);
    }

    /**
     * 不显示重新加载按钮
     */
    public void showEmptyContent() {
        showEmptyContent("暂无内容");
    }

    public void showEmptyContent(String text) {
        showEmptyView();
        emptyTextView.setText(text);
        emptyTextView.setVisibility(View.VISIBLE);
        emptyImageView.setVisibility(View.VISIBLE);
    }

    public void showEmptyView(int imageRes, String text) {
        emptyTextView.setText(text);
        emptyImageView.setImageResource(imageRes);
        emptyImageView.setVisibility(View.VISIBLE);
        showEmptyView();
    }


    public void showEmptyView(int imageRes) {
        emptyImageView.setImageResource(imageRes);
        emptyImageView.setVisibility(View.VISIBLE);
        showEmptyView();
    }

    public void showEmptyView(String error) {
        emptyTextView.setText(error);
        emptyTextView.setVisibility(View.VISIBLE);
        showEmptyView();
    }


    public void showEmptyView(int imageRes, String text, String buttonText, View.OnClickListener listener) {
        emptyImageView.setImageResource(imageRes);
        emptyImageView.setVisibility(View.VISIBLE);
        emptyTextView.setText(text);
        baseReload.setOnClickListener(listener);
        baseReload.setText(buttonText);
        baseReload.setVisibility(View.VISIBLE);
        showEmptyView();
    }

    public void setEmptyView(int imageRes, String text) {
        emptyTextView.setText(text);
        emptyImageView.setImageResource(imageRes);
        emptyImageView.setVisibility(View.VISIBLE);
    }

    public void setEmptyViewText(String text) {
        emptyTextView.setText(text);
        emptyTextView.setVisibility(View.VISIBLE);
    }

}
