package com.zhangwan.movieproject.app.adater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宋家任 on 2016/9/27.
 * 频道管理适配器
 */

public class ChannelPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> container;
    private static List<String> data = new ArrayList<>();

    public ChannelPagerAdapter(FragmentManager fm, ArrayList<Fragment> container, List<String> data) {
        super(fm);
        this.container = container;
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return container == null ? null : container.get(position);
    }

    @Override
    public int getCount() {
        return container == null ? 0 : container.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        if (container != null && container.size() == 0)
            return POSITION_NONE;
        else return POSITION_UNCHANGED;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        super.destroyItem(container, position, object);

    }
}
