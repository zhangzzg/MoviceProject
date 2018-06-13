
package com.gxtc.commlibrary.base;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.R;
import com.gxtc.commlibrary.widget.TitleRelativeLayout;


public class HeadView {

    private Button headCancelButton;
    private ImageButton headBackButton;
    private TextView headTitle;
    private Button headRightButton;
    private ImageButton HeadRightImageButton;
    private RelativeLayout headArea;
    private View headLine;
    private LinearLayout headRightLayout;

    public HeadView(View view) {
        headCancelButton = view.findViewById(R.id.headCancelButton);
        headBackButton = view.findViewById(R.id.headBackButton);
        headTitle = view.findViewById(R.id.headTitle);
        headRightButton = view.findViewById(R.id.headRightButton);
        HeadRightImageButton = view.findViewById(R.id.HeadRightImageButton);
        headArea = view.findViewById(R.id.headArea);
        headLine = view.findViewById(R.id.head_line);
        headRightLayout = view.findViewById(R.id.headRightLinearLayout);
    }

    public void showBaseHead() {
        headArea.setVisibility(View.VISIBLE);
    }

    public void hideBaseHead() {
        headArea.setVisibility(View.GONE);
    }

    public void hideHeadLine() {
        headLine.setVisibility(View.INVISIBLE);
    }

    public HeadView showTitle(String s) {
        headTitle.setText(s);
        return this;
    }


    public void showBackButton(View.OnClickListener listener) {
        headBackButton.setOnClickListener(listener);
        headBackButton.setVisibility(View.VISIBLE);
    }

    public void showBackButton(int res, View.OnClickListener listener) {
        headBackButton.setOnClickListener(listener);
        headBackButton.setImageResource(res);
        headBackButton.setVisibility(View.VISIBLE);
    }

    public void showCancelBackButton(String s, View.OnClickListener listener) {
        headCancelButton.setOnClickListener(listener);
        headCancelButton.setText(s);
        headCancelButton.setVisibility(View.VISIBLE);
    }

    public void showBackButton(String s, View.OnClickListener listener) {
        headRightButton.setOnClickListener(listener);
        headRightButton.setVisibility(View.VISIBLE);
        headRightButton.setText(s);
    }

    public void showHeadRightButton(String s, View.OnClickListener listener) {
        headRightButton.setOnClickListener(listener);
        headRightButton.setVisibility(View.VISIBLE);
        headRightButton.setText(s);
    }

    public void showHeadRightImageButton(int resId, View.OnClickListener listener) {
        HeadRightImageButton.setOnClickListener(listener);
        HeadRightImageButton.setVisibility(View.VISIBLE);
        HeadRightImageButton.setImageResource(resId);
    }

    public void changeHeadBackground(int resId) {
        headArea.setBackgroundResource(resId);
    }

    public RelativeLayout getParentView() {
        return headArea;
    }


    public void changeTitleTextColor(int color) {
        headTitle.setTextColor(color);
    }


    public LinearLayout getHeadRightLayout() {
        return headRightLayout;
    }

    public Button getHeadRightButton() {
        return headRightButton;
    }

    public void setHeadRightLayout(LinearLayout headRightLayout) {
        this.headRightLayout = headRightLayout;
    }

    public void hideHeadRightButton() {
        headRightButton.setVisibility(View.GONE);
    }

    public void hindBackButton() {
        headBackButton.setVisibility(View.GONE);
    }

    public TextView getHeadTitle() {
        return headTitle;
    }

    public ImageButton getHeadRightImageButton() {
        return HeadRightImageButton;
    }

}
