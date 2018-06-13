package com.zhangwan.movieproject.app.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by sjr on 2017/9/12.
 * 首页按钮
 */

public class TabBean implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabBean(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }

    public TabBean(String title) {
        this.title = title;
    }
}
