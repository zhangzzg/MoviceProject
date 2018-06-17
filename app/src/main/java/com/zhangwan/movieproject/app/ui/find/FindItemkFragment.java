package com.zhangwan.movieproject.app.ui.find;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gxtc.commlibrary.Constant;
import com.gxtc.commlibrary.base.BaseLazyFragment;
import com.gxtc.commlibrary.base.BaseTitleFragment;
import com.gxtc.commlibrary.recyclerview.RecyclerView;
import com.gxtc.commlibrary.recyclerview.wrapper.LoadMoreWrapper;
import com.gxtc.commlibrary.utils.GsonUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.adater.FindListAdater;
import com.zhangwan.movieproject.app.bean.ChannelBean;
import com.zhangwan.movieproject.app.bean.FindBean;
import com.zhangwan.movieproject.app.holder.RecyclerItemNormalHolder;
import com.zhangwan.movieproject.app.http.ApiCallBack;
import com.zhangwan.movieproject.app.http.ApiObserver;
import com.zhangwan.movieproject.app.http.ApiResponseBean;
import com.zhangwan.movieproject.app.http.service.HomeApiService;
import com.zhangwan.movieproject.app.utils.ErroreUtil;
import com.zhangwan.movieproject.app.widget.MPagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by zzg on 2018/4/11.
 */
public class FindItemkFragment extends BaseLazyFragment implements FindContract.View {
    @BindView(R.id.rl_news)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_news)
    SwipeRefreshLayout swipeNews;
    String chanelId;
    FindContract.Presenter mPresenter;
    int page = 1;
    public FindListAdater findListAdater;
    private LinearLayoutManager linearLayoutManager;

    public FindItemkFragment() {}

    @Override
    protected void onGetBundle(Bundle bundle) {
        super.onGetBundle(bundle);
        chanelId = bundle.getString("id");

    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_find_itemk, container, false);
    }

    @Override
    public void initData() {
        super.initData();
        swipeNews.setColorSchemeResources(Constant.REFRESH_COLOR);
        mRecyclerView.setLoadMoreView(R.layout.model_footview_loadmore);
        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        findListAdater = new FindListAdater(getContext(), new ArrayList<FindBean.FindSubBean>(), R.layout.find_article_item_layout, R.layout.find_video_item_layout);
        mRecyclerView.setAdapter(findListAdater);
        mRecyclerView.addOnScrollListener(new android.support.v7.widget.RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem   = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(RecyclerItemNormalHolder.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {

                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //是否全屏
                        releaseAllVideos();
                    }
                }
            }
        });

    }

    @Override
    public void initListener() {
        super.initListener();
        swipeNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                swipeNews.setRefreshing(false);
                mRecyclerView.reLoadFinish();
                mPresenter.getData(SpUtil.getToken(FindItemkFragment.this.getContext()), chanelId, null, null, page);
            }
        });
        mRecyclerView.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page = page + 1;
                mPresenter.getData(SpUtil.getToken(FindItemkFragment.this.getContext()), chanelId, null, null, page);
            }
        });


    }

    @Override
    public void showChannelData(List<ChannelBean> data) {}

    @Override
    public void showData(FindBean data) {
        getBaseLoadingView().hideLoading();
        if(page == 1){
            mRecyclerView.notifyChangeData(data.getLists(),findListAdater);
        }else {
            mRecyclerView.changeData(data.getLists(),findListAdater);
        }
    }


    @Override
    protected void lazyLoad() {
        if(mPresenter == null){
            new FindPresenter(this);
        }
        getBaseLoadingView().showLoading();
        mPresenter.getData(SpUtil.getToken(this.getContext()), chanelId, null, null, page);
    }

    //切换页面时如果有视屏播放则释放资源，停止视频的播放
    @Override
    protected void stopLoad() {
        releaseAllVideos();
    }

    public void releaseAllVideos(){
        if(!GSYVideoManager.isFullState(getActivity())) {
            GSYVideoManager.releaseAllVideos();
            findListAdater.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
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
        getBaseLoadingView().hideLoading();
        if (page == 1) {
            getBaseEmptyView().showEmptyContent();
        } else {
            mRecyclerView.loadFinish();
        }
    }

    @Override
    public void showReLoad() {
    }

    @Override
    public void showError(String code, String msg) {
        getBaseLoadingView().hideLoading();
        ErroreUtil.handleErrore(getActivity(),code,msg);
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if(mPresenter != null)
        mPresenter.destroy();
    }
}
