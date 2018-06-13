package com.zhangwan.movieproject.app.ui.message;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.base.BaseTitleFragment;
import com.gxtc.commlibrary.recyclerview.RecyclerView;
import com.gxtc.commlibrary.recyclerview.wrapper.LoadMoreWrapper;
import com.gxtc.commlibrary.utils.DateUtil;
import com.zhangwan.movieproject.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by zzg on 2018/4/11.
 */
public class MessageFragment extends BaseTitleFragment {
    @BindView(R.id.refreshlayout)   SwipeRefreshLayout refreshlayout;
    @BindView(R.id.recyclerView) RecyclerView       recyclerView;
    public MessageAdater messageAdater;
    public int page = 1;
    public MessageFragment() {}


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void initData() {
        super.initData();
        setActionBarTopPadding(getBaseHeadView().getParentView(),false);
        getBaseHeadView().showTitle("消息");
        refreshlayout.setColorSchemeResources( R.color.refresh_color1,
                R.color.refresh_color2,
                R.color.refresh_color3);
        recyclerView.setLoadMoreView(R.layout.model_footview_loadmore);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdater = new MessageAdater(getContext(),new ArrayList<String>(),R.layout.message_item_layout);
        recyclerView.setAdapter(messageAdater);
        getData();
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                recyclerView.reLoadFinish();
                getData();
            }
        });
        recyclerView.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page = page + 1;
                getData();
            }
        });
    }

    public void getData(){
        List<String> list = new ArrayList<>();
        for(int i = 0 ; i < 2; i++){
            list.add("系统消息");
        }
        if(messageAdater.getList().size() < 50){
            if(page == 1){
                refreshlayout.setRefreshing(false);
                recyclerView.notifyChangeData(list,messageAdater);
            }else {
                recyclerView.changeData(list,messageAdater);
            }
        }else {
            recyclerView.loadFinish();
        }
    }

    public static class MessageAdater extends BaseRecyclerAdapter<String> {

        public MessageAdater(Context context, List<String> list, int itemLayoutId) {
            super(context, list, itemLayoutId);
        }

        @Override
        public void bindData(ViewHolder holder, final int position, final String s) {
            holder.setText(R.id.name,s + position);
            holder.setText(R.id.time, DateUtil.stampToDate(String.valueOf(System.currentTimeMillis())));
            if(position % 5 != 0){
                holder.getImageView(R.id.image).setImageResource(R.drawable.system_gray_icom);
            }else {
                holder.getImageView(R.id.image).setImageResource(R.drawable.system_red_icom);
            }
        }
    }

}
