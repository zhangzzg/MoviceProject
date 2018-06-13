package com.gxtc.commlibrary.recyclerview.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.gxtc.commlibrary.R;
import com.gxtc.commlibrary.recyclerview.base.ViewHolder;
import com.gxtc.commlibrary.recyclerview.utils.WrapperUtils;


/**
 * Created by zhy on 16/6/23.
 */
public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private int DEFAULT_PAGE_COUNT = 10;      //只有大于这个数才能加载更多

    private RecyclerView.Adapter mInnerAdapter;
    private View                 mLoadMoreView;
    private int                  mLoadMoreLayoutId;
    private boolean canLoading = true;        //可以刷新标志
    private boolean loadmore   = false; //RecyclerView通过这个判读是否可以拿到更多


    public boolean isLoadmore() {
        return loadmore && canLoading;
    }

    public LoadMoreWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    public LoadMoreWrapper() {

    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    //加载更多完毕
    public void loadFinish() {
        canLoading = false;
        if (mLoadMoreView != null) {
            View progress = mLoadMoreView.findViewById(R.id.base_progressBar);
            if (progress != null) {
                progress.setVisibility(View.GONE);
            }
            View data = mLoadMoreView.findViewById(R.id.tv_no_data);
            if (data != null) {
                data.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 恢复可以加载更多
     */
    public void reLoadFinish() {
        canLoading = true;
        View progress = mLoadMoreView.findViewById(R.id.base_progressBar);
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
        View data = mLoadMoreView.findViewById(R.id.tv_no_data);
        if (data != null) {
            data.setVisibility(View.GONE);
        }
    }

    public boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    private boolean isShowLoadMore(int position) {
        if (hasLoadMore() && (position >= mInnerAdapter.getItemCount())) {
            return true;
        }
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position) && position >= DEFAULT_PAGE_COUNT - 1) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            ViewHolder holder;
            if (mLoadMoreView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        loadmore = false;
        if (isShowLoadMore(position)) {

            if (mOnLoadMoreListener != null && canLoading && position >= DEFAULT_PAGE_COUNT - 1) {
                loadmore = true;
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView,
                new WrapperUtils.SpanSizeCallback() {
                    @Override
                    public int getSpanSize(GridLayoutManager layoutManager,
                                           GridLayoutManager.SpanSizeLookup oldLookup,
                                           int position) {
                        if (isShowLoadMore(position)) {
                            return layoutManager.getSpanCount();
                        }
                        if (oldLookup != null) {
                            return oldLookup.getSpanSize(position);
                        }
                        return 1;
                    }
                });
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        if (hasLoadMore() && mInnerAdapter.getItemCount() > DEFAULT_PAGE_COUNT) {
            return mInnerAdapter.getItemCount() + 1;
        } else {
            return mInnerAdapter.getItemCount();
        }
    }


    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    /**
     * @param loadMoreView
     * @param defaultPage  只有大于这个数 才显示footerView
     * @return
     */
    public LoadMoreWrapper setLoadMoreView(View loadMoreView, int defaultPage) {
        DEFAULT_PAGE_COUNT = defaultPage < 0 ? 10 : defaultPage;
        mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        return this;
    }

    public View getLoadMoreView() {
        return mLoadMoreView;
    }

    public RecyclerView.Adapter getInnerAdapter() {
        return mInnerAdapter;
    }
}
