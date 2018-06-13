package com.gxtc.commlibrary.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    private FragmentManager     mManager;
    private FragmentTransaction mTransaction;
    private String curFragmentName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mManager = getSupportFragmentManager();
        //禁止旋转屏幕
        if (!canRotation()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //简化代码初始化
        ButterKnife.bind(this);
        initEvents(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    //绑定视图
    protected abstract int getLayoutId();

    //初始化事件
    protected abstract void initEvents(Bundle savedInstanceState);

    public void initView() {
    }

    public void initListener() {
    }

    public void initData() {
    }

    /**
     * 是否可以旋转屏幕
     */
    public boolean canRotation() {
        return false;
    }


    /**
     * 禁止点击菜单键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private Fragment mContent;

    /**
     * 替换fragment的方法
     *
     * @param fragment
     * @param simpleName
     * @param id
     */
    public void switchFragment(Fragment fragment, String simpleName, int id) {
        mTransaction = mManager.beginTransaction();
        curFragmentName = simpleName;
        if (fragment == null)
            return;

        if (mContent == null) {
            mTransaction.add(id, fragment, simpleName);
        } else {

            if (fragment.isAdded()) {
                mTransaction.hide(mContent).show(fragment);
            } else {
                mTransaction.hide(mContent).add(id, fragment, simpleName);
            }
        }
        mContent = fragment;
        mTransaction.commitAllowingStateLoss();
    }

    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }
}
