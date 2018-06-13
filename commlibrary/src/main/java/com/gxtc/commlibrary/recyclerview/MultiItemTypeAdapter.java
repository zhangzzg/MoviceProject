package com.gxtc.commlibrary.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.gxtc.commlibrary.recyclerview.base.IRecyclerViewAdapter;
import com.gxtc.commlibrary.recyclerview.base.ItemViewDelegate;
import com.gxtc.commlibrary.recyclerview.base.ItemViewDelegateManager;
import com.gxtc.commlibrary.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> implements
        IRecyclerViewAdapter<T> {

    public static final int NONE_TYPE = 99;

    protected Context mContext;
    protected List<T> mDatas;

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener     mOnItemClickListener;


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int              layoutId         = itemViewDelegate.getItemViewLayoutId();
        ViewHolder       holder           = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        return itemCount;
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public void addData(T item){
        if (mDatas != null) {
            mDatas.add(item);
            notifyDataSetChanged();
        }
    }


    public void addDatas(List<T> items){
        if (mDatas!=null){
            mDatas.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void changeDatas(List<T> items){
        if (mDatas!=null){
            mDatas.clear();
            mDatas.addAll(items);
            notifyDataSetChanged();
        }
    }


    /**
     * 带动画的删除
     *
     * @param position
     */
    @Override
    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position,mDatas.size() - position);

    }

    /**
     * 加载更多数据使用这个方法
     */
    @Override
    public void changeData(List<T> t) {
        if (t != null) {
            mDatas.addAll(t);
            notifyDataSetChanged();
        }
    }

    /**
     * 下拉刷新使用这个方法
     */

    @Override
    public void notifyChangeData(List<T> t) {
        if (t != null) {
            mDatas.clear();
            mDatas.addAll(t);
            notifyDataSetChanged();
        }
    }

    @Override
    public void changeData(List<T> t, int position) {
        if (t != null && mDatas != null && position <= mDatas.size() && position != -1) {
            mDatas.addAll(position, t);
            notifyDataSetChanged();
        }
    }
}
