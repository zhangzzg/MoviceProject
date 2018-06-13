package com.zhangwan.movieproject.app.ui.find;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.base.BaseTitleFragment;
import com.gxtc.commlibrary.utils.GsonUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.adater.ChannelPagerAdapter;
import com.zhangwan.movieproject.app.bean.ChannelBean;
import com.zhangwan.movieproject.app.bean.FindBean;
import com.zhangwan.movieproject.app.utils.ErroreUtil;
import com.zhangwan.movieproject.app.utils.StatusBarUtils;
import com.zhangwan.movieproject.app.widget.MPagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zzg on 2018/4/11.
 */
public class FindFragment extends BaseTitleFragment implements FindContract.View {
     @BindView(R.id.pss_tab)        MPagerSlidingTabStrip pssTab;
     @BindView(R.id.vp_fragment)    ViewPager             viewPager;
     @BindView(R.id.iv_news_search) ImageView             serach;
     @BindView(R.id.header_layout)   View             headerView;
     FindContract.Presenter mPresenter;
    //新闻条目
    private ArrayList<Fragment> container = new ArrayList<>();
    private ChannelPagerAdapter mAdapter;
    private List<String> datas = new ArrayList<>();
    public List<ChannelBean> data;
    private FindItemkFragment fragment;

    public FindFragment() {
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void initData() {
        super.initData();
        //将标题栏的高度设置成状态栏的高度，从而方便标题栏刚刚适合状态栏
        getBaseHeadView().getParentView().getLayoutParams().height = getStatusBarHeight();
        getBaseHeadView().getParentView().setBackgroundColor(Color.argb((int) 125, 13, 13, 13));
        new FindPresenter(this);
        mPresenter.getChannelData(SpUtil.getToken(getContext()));
    }

    @Override
    public void initListener() {
        super.initListener();
        serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchActivity.class);
            }
        });
    }

    private void initVp() {
        if(data == null || data.size() == 0) return;
        Observable.from(data).subscribe(new Action1<ChannelBean>() {
            @Override
            public void call(ChannelBean newChannelItem) {
                fragment = new FindItemkFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", newChannelItem.getCid());
                bundle.putString("name", newChannelItem.getName());
                fragment.setArguments(bundle);
                datas.add(newChannelItem.getName());
                container.add(fragment);
            }
        });

        mAdapter = new ChannelPagerAdapter(getChildFragmentManager(), this.container, datas);

        viewPager.setAdapter(mAdapter);
        viewPager.getParent().requestDisallowInterceptTouchEvent(true);
        viewPager.setOffscreenPageLimit(1);
        pssTab.setViewPager(viewPager);
        pssTab.invalidate();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            GSYVideoManager.releaseAllVideos();
        }
    }

    @Override
    public void showChannelData(List<ChannelBean> data) {
        this.data = data;
        initVp();
    }

    @Override
    public void showData(FindBean data) {
    }

    @Override
    public void setPresenter(FindContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void showLoadFinish() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
        ErroreUtil.handleErrore(getActivity(),code,msg);
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
