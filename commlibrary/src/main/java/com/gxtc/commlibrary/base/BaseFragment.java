package com.gxtc.commlibrary.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Steven on 16/10/8.
 */

public abstract class BaseFragment extends Fragment {

    protected View     baseRootView;
    private   Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseRootView = initView(inflater,container);
        unbinder = ButterKnife.bind(this,baseRootView);
        onGetBundle(getArguments());
        initListener();
        initData();

        return baseRootView;
    }

    /**
     * onCreatView 中调用
     * @param inflater
     * @param container
     * @return
     */
    public abstract View initView(LayoutInflater inflater, ViewGroup container);

    /**
     * onCreatView 中调用
     */
    public void initListener(){}

    public void initData(){}


    /**
     * 获取fragmentView
     * @return fragmentView
     */
    protected View getRootView() {
        return this.baseRootView;
    }

    /**
     * onCreateView中调用
     * 可以获取上一个Fragment传过来的数据
     */
    protected void onGetBundle(Bundle bundle) {}


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除绑定
        if(unbinder != null){
            unbinder.unbind();
        }
    }

}
