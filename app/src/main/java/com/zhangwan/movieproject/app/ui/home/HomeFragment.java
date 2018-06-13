package com.zhangwan.movieproject.app.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gxtc.commlibrary.base.BaseMoreTypeRecyclerAdapter;
import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.base.BaseTitleFragment;
import com.gxtc.commlibrary.recyclerview.RecyclerView;
import com.gxtc.commlibrary.recyclerview.wrapper.LoadMoreWrapper;
import com.gxtc.commlibrary.utils.ActivityController;
import com.gxtc.commlibrary.utils.GsonUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.adater.BannerAdapter;
import com.zhangwan.movieproject.app.adater.HomeVideoAdater;
import com.zhangwan.movieproject.app.adater.PlatFormAdater;
import com.zhangwan.movieproject.app.bean.HomeBean;
import com.zhangwan.movieproject.app.ui.common.CommonWebViewActivity;
import com.zhangwan.movieproject.app.ui.login.LoginActivity;
import com.zhangwan.movieproject.app.ui.register.RegisterActivity;
import com.zhangwan.movieproject.app.utils.ErroreUtil;
import com.zhangwan.movieproject.app.utils.MyDialogUtil;
import com.zhangwan.movieproject.app.utils.StatusBarUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zzg on 2018/4/11.
 */
public class HomeFragment extends BaseTitleFragment implements HomeContract.View,NestedScrollView.OnScrollChangeListener {
    @BindView(R.id.ns_bookstore)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;
    @BindView(R.id.recyclerView)
    android.support.v7.widget.RecyclerView recyclerView;
    @BindView(R.id.platform_list)
    android.support.v7.widget.RecyclerView platFormList;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.cb_live_banner)
    ConvenientBanner mConvenientBanner;

    HomeContract.Presenter mPresenter;

    public PlatFormAdater mPlatFormAdater;
    public HomeBean mHomeBean;
    public HomeVideoAdater homeVideoAdater;
    private GridLayoutManager gridLayoutManager;

    public HomeFragment() {
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData() {
        super.initData();
        setActionBarTopPadding(title,true);
        title.setText(getString(R.string.app_name));
        mNestedScrollView.setOnScrollChangeListener(this);
        refreshlayout.setColorSchemeResources(R.color.refresh_color1,
                R.color.refresh_color2,
                R.color.refresh_color3);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        platFormList.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mPlatFormAdater = new PlatFormAdater(getContext(), new ArrayList<HomeBean.Platform>(), R.layout.platform_item_layout);
        platFormList.setAdapter(mPlatFormAdater);
        homeVideoAdater = new HomeVideoAdater(getContext(), new ArrayList<HomeBean.Video>(), R.layout.home_layout_big_item_layout, R.layout.home_layout_small_item_layout);
        recyclerView.setAdapter(homeVideoAdater);
        recyclerView.setNestedScrollingEnabled(false);
        platFormList.setNestedScrollingEnabled(false);
        new HomePresenter(this);
        mPresenter.getHomeData(SpUtil.getToken(this.getContext()));
        homeVideoAdater.setOnReItemOnClickListener(new BaseMoreTypeRecyclerAdapter.OnReItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(TextUtils.isEmpty(homeVideoAdater.getDatas().get(position).getUrl())){
                    CommonWebViewActivity.startActivity(getContext(),"http://zhuangb.zwlqh.cn/index.php/Home/Mgtv/lists2/ismovie/2?url=http%3A%2F%2Fwww.mgtv.com%2Fb%2F321778%2F4328000.html");
                }else {
                    CommonWebViewActivity.startActivity(getContext(),homeVideoAdater.getDatas().get(position).getUrl());
                }
            }
        });
        mPlatFormAdater.setOnItemClickLisntener(new BaseRecyclerAdapter.OnItemClickLisntener() {
            @Override
            public void onItemClick(android.support.v7.widget.RecyclerView parentView, View v,
                                    int position) {
                //1 表示游客身份
                if("1".equals(SpUtil.getGrade(getContext()))){
                    MyDialogUtil.noticeDialog(getContext(), "游客权限不足,请先注册登录,方可浏览,是否注册?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(),RegisterActivity.class);
                            intent.putExtra("flag",true);
                            startActivity(intent);
                        }
                    });
                }else {
                    CommonWebViewActivity.startActivity(getContext(),mPlatFormAdater.getList().get(position).getUrl());
                }
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getHomeData(SpUtil.getToken(HomeFragment.this.getContext()));
            }
        });
    }


    @Override
    public void showHomeData(HomeBean data) {
        refreshlayout.setRefreshing(false);
        mHomeBean = data;
        setAd();
        setPlatform();
        setVideo();
    }

    private void setAd() {
        mConvenientBanner.startTurning(4000);
        mConvenientBanner.setPages(new CBViewHolderCreator<BannerAdapter>() {
            @Override
            public BannerAdapter createHolder() {
                return new BannerAdapter();
            }
        }, mHomeBean.getAd());
        mConvenientBanner.setPageIndicator(
                new int[]{R.drawable.news_icon_dot_small, R.drawable.news_icon_dot_big});
        mConvenientBanner.setPageIndicatorAlign(
                ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        mConvenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CommonWebViewActivity.startActivity(getContext(),mHomeBean.getAd().get(position).getUrl());
            }
        });

    }

    private void setPlatform() {
        if (mHomeBean != null && mHomeBean.getPlatform() != null)
            mPlatFormAdater.notifyChangeData(mHomeBean.getPlatform());
    }

    private void setVideo() {
        if (mHomeBean == null || mHomeBean.getVideo() == null) return;
        homeVideoAdater.notifyChangeData(mHomeBean.getVideo());
        //合并单元格
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (position < homeVideoAdater.getDatas().size()) {
                    if ("1".equals(homeVideoAdater.getDatas().get(position).getIsfirst())) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
                return 2;
            }
        });
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoad() {
        getBaseLoadingView().showLoading();
        hideContentLayout();
    }

    @Override
    public void showLoadFinish() {
        getBaseLoadingView().hideLoading();
        showContentLayout();
    }

    @Override
    public void showEmpty() {
        getBaseEmptyView().showEmptyContent();
        showContentLayout();
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

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
                               int oldScrollY) {
        if (scrollY <= 0) {  //设置标题的背景颜色
            title.setAlpha(0);
        } else if (scrollY <= mConvenientBanner.getHeight()) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float alpha = (float) scrollY / mConvenientBanner.getHeight();
            title.setAlpha(alpha);
        } else {  //滑动到banner下面设置普通颜色
            title.setAlpha(1);
        }
    }
}