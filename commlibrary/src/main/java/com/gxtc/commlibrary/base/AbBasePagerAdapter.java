package com.gxtc.commlibrary.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 抽象pagerdapter类
 *
 * @param <T>
 * @author Administrator
 */
public abstract class AbBasePagerAdapter<T> extends PagerAdapter {

    private List<View> views;
    private List<T> datas;
    private Context context;

    public AbBasePagerAdapter(Context context, List<View> views, List<T> datas) {
        this.views = views;
        this.datas = datas;
        this.context = context;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        T t = datas.get(position);
        bindData(view, t,position);
        container.addView(view);
        return view;
    }

    /**
     * 填充数据到View上面
     *
     * @param view
     * @param t    对应position的数据对象
     */
    public abstract void bindData(View view, T t,int position);


    public List<T> getDatas() {
        return datas;
    }


    public void setDatas(List<T> datas) {
        this.datas = datas;
    }


    public Context getContext() {
        return context;
    }


    public void setContext(Context context) {
        this.context = context;
    }


}
