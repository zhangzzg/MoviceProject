package com.gxtc.commlibrary.base;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.recyclerview.base.IRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的多布局封装适配器
 */

public abstract class BaseRecyclerTypeAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        IRecyclerViewAdapter<T> {

    private Context mContext;
    private List<T> mDatas;

    private List<ViewHolder> holders;

    private int[] resId;
    private OnReItemOnClickListener itemListener;
    private OnReItemOnLongClickListener longListener;

    public BaseRecyclerTypeAdapter(Context mContext, List<T> mDatas, int[] resId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.resId = resId;
        holders = new ArrayList<>();
    }

    /**
     * 抽象方法
     */
    protected abstract int getViewType(int position);

    protected abstract void bindData(RecyclerView.ViewHolder holder, int position, int type, T t);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(resId[viewType], parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener != null) {
                    itemListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longListener != null) {
                    longListener.onItemLongClick(v, (Integer) v.getTag());
                }
                return false;
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewholder = (ViewHolder) holder;
        viewholder.getItemView().setTag(position);
        bindData(holder,position,getItemViewType(position),mDatas.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    /**
     * viewholder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ArrayMap<Integer, View> viewMap;
        private View                    itemView;
        private Object                  mObject;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            viewMap = new ArrayMap<Integer, View>();
        }

        public View getView(int viewId) {
            View view = viewMap.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                viewMap.put(viewId, view);
            }

            return view;
        }

        public ViewHolder setDataTag(Object tag) {
            mObject = tag;
            return this;
        }

        public Object getDataTag() {
            return mObject;
        }


        public ImageView getImageView(int viewid) {
            return (ImageView) getView(viewid);
        }

        /**
         * 查找view  用这个方法不需要强转  内部已经帮转好了。
         *
         * @param viewId
         * @param <T>    view的子类
         * @return 查找到的view
         */
        public <T extends View> T getViewV2(int viewId) {
            View view = viewMap.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                viewMap.put(viewId, view);
            }
            return (T) view;
        }


        /**
         * 设置文字
         *
         * @param viewId 要设置文字的 ImageView ID
         * @param str    文字的资源ID
         * @return
         */
        public ViewHolder setText(int viewId, String str) {
            ((TextView) getViewV2(viewId)).setText(str);
            return this;
        }

        /**
         * 设置图片
         *
         * @param viewId 要设置图片的 ImageView ID
         * @param resId  图片的资源ID
         * @return
         */
        public ViewHolder setImage(int viewId, int resId) {
            ((ImageView) getViewV2(viewId)).setImageResource(resId);
            return this;
        }

        /**
         * 设置点击事件
         *
         * @param viewId 要设置的控件的id
         * @param l      点击事件
         * @return
         */
        public ViewHolder setOnClick(int viewId, View.OnClickListener l) {
            getView(viewId).setOnClickListener(l);
            return this;
        }

        public View getItemView() {
            return itemView;
        }

    }


    /**
     * listener
     */
    public interface OnReItemOnClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnReItemOnLongClickListener {
        void onItemLongClick(View v, int position);
    }

    public void setOnReItemOnClickListener(OnReItemOnClickListener listener) {
        this.itemListener = listener;
    }

    public void setOnReItemOnLongClickListener(OnReItemOnLongClickListener longListener) {
        this.longListener = longListener;
    }

    public Context getmContext() {
        return mContext;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void addData(int position, T t) {
        mDatas.add(position, t);
        notifyItemInserted(position);
    }

    public void addData(T t) {
        mDatas.add(t);
        notifyItemInserted(mDatas.size() - 1);
    }

    @Override
    public void removeData(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
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
